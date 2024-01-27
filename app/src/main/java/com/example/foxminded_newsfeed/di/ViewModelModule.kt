package com.example.foxminded_newsfeed.di

import com.example.foxminded_newsfeed.domain.usecase.ChangeFavoriteStatusUseCase
import com.example.foxminded_newsfeed.domain.usecase.GetFavoriteNewsUseCase
import com.example.foxminded_newsfeed.domain.usecase.GetNewsUseCase
import com.example.foxminded_newsfeed.ui.MainViewModel
import com.example.foxminded_newsfeed.ui.UIState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ViewModelModule {

    @Provides
    @Singleton
    fun mainActivityVMProvider(
        getNewsUseCase: GetNewsUseCase,
        mutableStateFlow: MutableStateFlow<UIState>,
        changeFavoriteStatusUseCase: ChangeFavoriteStatusUseCase,
        getFavoriteNewsUseCase: GetFavoriteNewsUseCase
    ): MainViewModel {
        return MainViewModel(
            getNewsUseCase = getNewsUseCase,
            uiState = mutableStateFlow,
            changeFavoriteStatusUseCase = changeFavoriteStatusUseCase,
            getFavoriteNewsUseCase = getFavoriteNewsUseCase
        )
    }

    @Provides
    @Singleton
    fun generalStateProvider(): UIState {
        return UIState()
    }

    @Provides
    @Singleton
    fun generalMutableStateProvider(uiState: UIState): MutableStateFlow<UIState> {
        return MutableStateFlow(uiState)
    }
}