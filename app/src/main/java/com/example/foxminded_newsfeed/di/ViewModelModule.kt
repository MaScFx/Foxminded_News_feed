package com.example.foxminded_newsfeed.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.foxminded_newsfeed.domain.usecase.ClickFavoriteButtonOnItem
import com.example.foxminded_newsfeed.domain.usecase.GetFavoriteNews
import com.example.foxminded_newsfeed.domain.usecase.GetNews
import com.example.foxminded_newsfeed.ui.MainActivityVM
import com.example.foxminded_newsfeed.ui.UIState
import com.example.foxminded_newsfeed.ui.screen.allNews.AllNewsVM
import com.example.foxminded_newsfeed.ui.screen.favoriteNews.FavoriteNewsVM
import com.example.foxminded_newsfeed.ui.screen.newsFromSelectedProvider.NewsFromSelectedProviderVM
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ViewModelModule {

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun mainActivityVMProvider(
        getNews: GetNews,
        mutableStateFlow: MutableStateFlow<UIState>
    ): MainActivityVM {
        return MainActivityVM(
            getNews = getNews,
            generalUIState = mutableStateFlow
        )
    }
    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun allNewsVMProvider(
        getNews: GetNews,
        clickFavoriteButtonOnItem: ClickFavoriteButtonOnItem,
        mutableStateFlow: MutableStateFlow<UIState>
    ): AllNewsVM {
        return AllNewsVM(
            getNews = getNews,
            clickFavoriteButtonOnItem = clickFavoriteButtonOnItem,
            generalUIState = mutableStateFlow
        )
    }

    @Provides
    @Singleton
    fun favoriteNewsVMProvider(
        getFavoriteNews: GetFavoriteNews,
        clickFavoriteButtonOnItem: ClickFavoriteButtonOnItem,
        mutableStateFlow: MutableStateFlow<UIState>
    ): FavoriteNewsVM {
        return FavoriteNewsVM(
            getFavoriteNews = getFavoriteNews,
            clickFavoriteButtonOnItem = clickFavoriteButtonOnItem,
            generalUIState = mutableStateFlow
        )
    }

    @Provides
    @Singleton
    fun newsFromSelectedProviderVMProvider(
        clickFavoriteButtonOnItem: ClickFavoriteButtonOnItem,
        mutableStateFlow: MutableStateFlow<UIState>
    ): NewsFromSelectedProviderVM {
        return NewsFromSelectedProviderVM(
            clickFavoriteButtonOnItem = clickFavoriteButtonOnItem,
            generalUIState = mutableStateFlow
        )
    }

    @Provides
    @Singleton
    fun generalStateProvider(): UIState {
        return UIState()
    }

    @Provides
    @Singleton
    fun generalMutableStateProvider(uiState: UIState):MutableStateFlow<UIState>{
        return MutableStateFlow(uiState)
    }
}