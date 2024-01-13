package com.example.foxminded_newsfeed.ui.screen.favoriteNews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foxminded_newsfeed.domain.model.NewsItem
import com.example.foxminded_newsfeed.domain.usecase.ClickFavoriteButtonOnItem
import com.example.foxminded_newsfeed.domain.usecase.GetFavoriteNews
import com.example.foxminded_newsfeed.ui.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteNewsVM @Inject constructor(
    private val getFavoriteNews: GetFavoriteNews,
    private val clickFavoriteButtonOnItem: ClickFavoriteButtonOnItem,
    private val generalUIState: MutableStateFlow<UIState>
) :
    ViewModel() {

    //    private val _uiState = MutableStateFlow(generalUIState)
//    val uiState: StateFlow<UIState> = s.asStateFlow()
    val favoriteNewsFlow = getFavoriteNews.get()
    fun clickFavoriteButton(newsItem: NewsItem) {
        viewModelScope.launch {
            clickFavoriteButtonOnItem.cLick(newsItem)
        }
        generalUIState.update { state ->
            val newState: MutableList<NewsItem> = ArrayList()
            state.allNewsList.forEach { item ->
                if (item.id == newsItem.id) {
                    newState.add(item.copy(isFavorites = if (item.isFavorites == 1) 0 else 1))
                } else {
                    newState.add(item)
                }
            }
            state.copy(allNewsList = newState.toList())
        }
        if (generalUIState.value.selectedNewsList.isNotEmpty()){
            generalUIState.update { state ->
                state.copy(selectedNewsList = state.allNewsList.filter { it.newsSource == state.selectedNewsList[0].newsSource })
            }
        }
    }

//    init {
//        viewModelScope.launch {
//            newsFlow = getFavoriteNews.get().stateIn(viewModelScope)

//            val itemList: List<NewsItem> = getFavoriteNews.get().coll
//
//            _uiState.update { c ->
//                c.copy(newsList = itemList)
//            }
//        }
//
//    }
}