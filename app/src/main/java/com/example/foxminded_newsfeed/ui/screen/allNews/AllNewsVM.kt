package com.example.foxminded_newsfeed.ui.screen.allNews

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foxminded_newsfeed.domain.model.NewsItem
import com.example.foxminded_newsfeed.domain.model.UseCaseAnswer
import com.example.foxminded_newsfeed.domain.usecase.ClickFavoriteButtonOnItem
import com.example.foxminded_newsfeed.domain.usecase.GetNews
import com.example.foxminded_newsfeed.ui.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class AllNewsVM @Inject constructor(
    private val getNews: GetNews,
    private val clickFavoriteButtonOnItem: ClickFavoriteButtonOnItem,
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
            clickFavoriteButtonOnItem.cLick(newsItem)
        }
    }

    fun hideErrorMessage(){
        generalUIState.update { state ->
            state.copy(
                showInternetConnectionError = false
            )
        }
    }

//    init {
//        viewModelScope.launch {
//            val getNewsAnswer: UseCaseAnswer = getNews.get()
//
//            generalUIState.update { state ->
//                state.copy(
//                    allNews = getNewsAnswer.resultList,
//                    showInternetConnectionError = getNewsAnswer.internetIsAvailable
//                )
//            }
//        }
//    }
}