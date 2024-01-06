package com.example.foxminded_newsfeed.vm

import androidx.lifecycle.ViewModel
import com.example.foxminded_newsfeed.domain.model.NewsItem
import com.example.foxminded_newsfeed.domain.usecase.GetNews
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AllNewsVM @Inject constructor(private val getNews: GetNews) : ViewModel() {

    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    init {
        val a:List<NewsItem> = getNews.get()
        a[0].title = "azazaz-allNews"

        _uiState.update { c ->
            c.copy(newsList = a)
        }
    }


}