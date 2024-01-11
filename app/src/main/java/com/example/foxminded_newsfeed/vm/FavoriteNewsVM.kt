package com.example.foxminded_newsfeed.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foxminded_newsfeed.domain.model.NewsItem
import com.example.foxminded_newsfeed.domain.usecase.GetFavoriteNews
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteNewsVM @Inject constructor(private val getFavoriteNews: GetFavoriteNews) :
    ViewModel() {

    //    private val _uiState = MutableStateFlow(UIState())
//    val uiState: StateFlow<UIState> = _uiState.asStateFlow()
    val favoriteNewsFlow = getFavoriteNews.get()

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