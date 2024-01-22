package com.example.foxminded_newsfeed.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foxminded_newsfeed.domain.model.UseCaseAnswer
import com.example.foxminded_newsfeed.domain.usecase.GetNewsUseCase
import com.example.foxminded_newsfeed.ui.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityVM @Inject constructor(
    val generalUIState: MutableStateFlow<UIState>, val getNewsUseCase: GetNewsUseCase
) : ViewModel() {

    fun refreshNews() = viewModelScope.launch {
        generalUIState.update { state ->
            state.copy(
                isRefreshing = true
            )
        }

        val getNewsAnswer: UseCaseAnswer = getNewsUseCase.invoke()

        generalUIState.update { state ->
            state.copy(
                allNews = getNewsAnswer.resultList,
                showInternetConnectionError = getNewsAnswer.internetIsAvailable,
                isRefreshing = false
            )
        }
    }

    init {
        refreshNews()
    }
}