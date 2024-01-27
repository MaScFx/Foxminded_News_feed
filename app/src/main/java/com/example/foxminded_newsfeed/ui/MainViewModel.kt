package com.example.foxminded_newsfeed.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foxminded_newsfeed.data.room.NewsEntity
import com.example.foxminded_newsfeed.domain.model.GetNewsAnswer
import com.example.foxminded_newsfeed.domain.model.NewsSource
import com.example.foxminded_newsfeed.domain.usecase.ChangeFavoriteStatusUseCase
import com.example.foxminded_newsfeed.domain.usecase.GetFavoriteNewsUseCase
import com.example.foxminded_newsfeed.domain.usecase.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val uiState: MutableStateFlow<UIState>,
    val getNewsUseCase: GetNewsUseCase,
    private val changeFavoriteStatusUseCase: ChangeFavoriteStatusUseCase,
    private val getFavoriteNewsUseCase: GetFavoriteNewsUseCase,

    ) : ViewModel() {

    fun refreshNews() {
        uiState.update { state ->
            state.copy(
                isRefreshing = true
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            val getNewsAnswer: GetNewsAnswer = getNewsUseCase.invoke()
            uiState.update { state ->
                state.copy(
                    allNews = getNewsAnswer.resultList,
                    showInternetConnectionError = getNewsAnswer.internetIsAvailable,
                    isRefreshing = false
                )
            }
        }
    }

    suspend fun favoriteNewsShow() {
        viewModelScope.launch(Dispatchers.IO) {
            getFavoriteNewsUseCase.invoke().collect {
                uiState.update { state ->
                    state.copy(favoriteNews = it)
                }
            }
        }
    }

    fun newsFromSelectedSource(newsSource: NewsSource) {
        uiState.update { state ->
            state.copy(selectedNews = state.allNews.filter { it.newsSource == newsSource })
        }
    }

    fun clickFavoriteButton(newsEntity: NewsEntity) {
        uiState.update { state ->
            val newState: MutableList<NewsEntity> = ArrayList()

            state.allNews.forEach { item ->
                if (item.id == newsEntity.id) {
                    newState.add(item.copy(isFavorite = if (item.isFavorite == 1) 0 else 1))
                } else {
                    newState.add(item)
                }
            }
            state.copy(allNews = newState.toList())
        }
        if (uiState.value.selectedNews.isNotEmpty()) {
            uiState.update { state ->
                state.copy(selectedNews = state.allNews.filter { it.newsSource == state.selectedNews[0].newsSource })
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            changeFavoriteStatusUseCase.invoke(newsEntity)
        }
    }

    fun hideErrorMessage() {
        uiState.update { state ->
            state.copy(
                showInternetConnectionError = false
            )
        }
    }
}