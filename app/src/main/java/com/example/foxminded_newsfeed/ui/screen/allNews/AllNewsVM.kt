package com.example.foxminded_newsfeed.ui.screen.allNews

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foxminded_newsfeed.domain.model.NewsItem
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
class AllNewsVM @Inject constructor(private val getNews: GetNews) : ViewModel() {

    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch{
            val itemList:List<NewsItem> = getNews.get()

            _uiState.update { c ->
                c.copy(newsList = itemList)
            }
        }
    }


}