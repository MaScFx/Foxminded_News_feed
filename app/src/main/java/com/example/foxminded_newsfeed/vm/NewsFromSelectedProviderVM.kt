package com.example.foxminded_newsfeed.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foxminded_newsfeed.domain.model.NewsSource
import com.example.foxminded_newsfeed.domain.usecase.GetNewsFromSelectedProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsFromSelectedProviderVM @Inject constructor(
    private val getNewsFromSelectedProvider: GetNewsFromSelectedProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    fun newsFromSelectedSource(newsSource: NewsSource) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(newsList = getNewsFromSelectedProvider.get(newsSource))
            }
        }
    }

//    init {
//        viewModelScope.launch {
//            _uiState.update { c ->
//                c.copy(newsList = getNewsFromSelectedProvider.get(NewsSource.WELT))
//            }
//        }
//
//    }
}