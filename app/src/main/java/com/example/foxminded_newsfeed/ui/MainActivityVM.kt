package com.example.foxminded_newsfeed.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foxminded_newsfeed.domain.model.UseCaseAnswer
import com.example.foxminded_newsfeed.domain.usecase.GetNews
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityVM @Inject constructor(
    val generalUIState: MutableStateFlow<UIState>,
    val getNews: GetNews
) : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    fun refreshNews() = viewModelScope.launch {
        generalUIState.update { state ->
            state.copy(
                isRefreshing = true
            )
        }

        val getNewsAnswer: UseCaseAnswer = getNews.get()

        generalUIState.update { state ->
            state.copy(
                allNews = getNewsAnswer.resultList,
                showInternetConnectionError = getNewsAnswer.internetIsAvailable,
                isRefreshing = false
            )
        }
    }

}