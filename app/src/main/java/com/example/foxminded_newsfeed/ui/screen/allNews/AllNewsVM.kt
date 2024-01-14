package com.example.foxminded_newsfeed.ui.screen.allNews

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foxminded_newsfeed.domain.model.NewsItem
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

            state.allNewsList.forEach { item ->
                if (item.id == newsItem.id) {
                    newState.add(item.copy(isFavorites = if (item.isFavorites == 1) 0 else 1))
                } else {
                    newState.add(item)
                }
            }
            state.copy(allNewsList = newState.toList())
        }
        if (generalUIState.value.selectedNewsList.isNotEmpty()) {
            generalUIState.update { state ->
                state.copy(selectedNewsList = state.allNewsList.filter { it.newsSource == state.selectedNewsList[0].newsSource })
            }
        }

        viewModelScope.launch {
            clickFavoriteButtonOnItem.cLick(newsItem)
        }
    }

//    fun onItemClick(newsItem:NewsItem){
//        val url = newsItem.link
//        val intent :CustomTabsIntent  = CustomTabsIntent.Builder()
//            .build();
//        intent.launchUrl(LocalContext.current, Uri.parse(url));
//
//    }


    init {
        viewModelScope.launch {
            val itemList: List<NewsItem> = getNews.get()

            generalUIState.update { c ->
                c.copy(allNewsList = itemList)
            }
        }
    }
}