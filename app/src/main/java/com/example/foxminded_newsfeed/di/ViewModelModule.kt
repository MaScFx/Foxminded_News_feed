package com.example.foxminded_newsfeed.di

import com.example.foxminded_newsfeed.domain.usecase.ChangeFavoriteStatusUseCase
import com.example.foxminded_newsfeed.domain.usecase.GetFavoriteNewsUseCase
import com.example.foxminded_newsfeed.domain.usecase.GetNewsUseCase
import com.example.foxminded_newsfeed.ui.UIState
import com.example.foxminded_newsfeed.ui.main.MainActivityVM
import com.example.foxminded_newsfeed.ui.screen.allNews.AllNewsVM
import com.example.foxminded_newsfeed.ui.screen.favoriteNews.FavoriteNewsVM
import com.example.foxminded_newsfeed.ui.screen.selectedNews.SelectedNewsVM
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
        getNewsUseCase: GetNewsUseCase, mutableStateFlow: MutableStateFlow<UIState>
    ): MainActivityVM {
        return MainActivityVM(getNewsUseCase = getNewsUseCase, generalUIState = mutableStateFlow)
    }

    @Provides
    @Singleton
    fun allNewsVMProvider(
        changeFavoriteStatusUseCase: ChangeFavoriteStatusUseCase,
        mutableStateFlow: MutableStateFlow<UIState>
    ): AllNewsVM {
        return AllNewsVM(
            changeFavoriteStatusUseCase = changeFavoriteStatusUseCase,
            generalUIState = mutableStateFlow
        )
    }

    @Provides
    @Singleton
    fun favoriteNewsVMProvider(
        getFavoriteNewsUseCase: GetFavoriteNewsUseCase,
        changeFavoriteStatusUseCase: ChangeFavoriteStatusUseCase,
        mutableStateFlow: MutableStateFlow<UIState>
    ): FavoriteNewsVM {
        return FavoriteNewsVM(
            getFavoriteNewsUseCase = getFavoriteNewsUseCase,
            changeFavoriteStatusUseCase = changeFavoriteStatusUseCase,
            generalUIState = mutableStateFlow
        )
    }

    @Provides
    @Singleton
    fun newsFromSelectedProviderVMProvider(
        changeFavoriteStatusUseCase: ChangeFavoriteStatusUseCase,
        mutableStateFlow: MutableStateFlow<UIState>
    ): SelectedNewsVM {
        return SelectedNewsVM(
            changeFavoriteStatusUseCase = changeFavoriteStatusUseCase, generalUIState = mutableStateFlow
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