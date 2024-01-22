package com.example.foxminded_newsfeed.ui.screen.selectedNews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foxminded_newsfeed.domain.model.NewsItem
import com.example.foxminded_newsfeed.domain.model.NewsSource
import com.example.foxminded_newsfeed.domain.usecase.ChangeFavoriteStatusUseCase
import com.example.foxminded_newsfeed.ui.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectedNewsVM @Inject constructor(
    private val changeFavoriteStatusUseCase: ChangeFavoriteStatusUseCase,
    private val generalUIState: MutableStateFlow<UIState>
) : ViewModel() {

    val uiState: StateFlow<UIState> = generalUIState.asStateFlow()
    fun clickFavoriteButton(newsItem: NewsItem) {
        generalUIState.update { state ->
            val newState: MutableList<NewsItem> = ArrayList()
            state.allNews.forEach { item ->
                if (item.id == newsItem.id) {
                    newState.add(item.copy(isFavorites = if (item.isFavorites == 1) 0 else 1))
                } else {
                    newState.add(item)
                }
            }
            state.copy(allNews = newState.toList())
        }
        if (generalUIState.value.selectedNews.isNotEmpty()) {
            generalUIState.update { state ->
                state.copy(selectedNews = state.allNews.filter { it.newsSource == state.selectedNews[0].newsSource })
            }
        }
        viewModelScope.launch {
            changeFavoriteStatusUseCase.invoke(newsItem)
        }
    }

    fun newsFromSelectedSource(newsSource: NewsSource) {
        viewModelScope.launch {
            generalUIState.update { state ->
                state.copy(selectedNews = state.allNews.filter { it.newsSource == newsSource })
            }
        }
    }
}