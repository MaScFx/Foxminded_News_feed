package com.example.foxminded_newsfeed.di

import com.example.foxminded_newsfeed.domain.usecase.GetFavoriteNews
import com.example.foxminded_newsfeed.domain.usecase.GetNews
import com.example.foxminded_newsfeed.domain.usecase.GetNewsFromSelectedProvider
import com.example.foxminded_newsfeed.ui.screen.allNews.AllNewsVM
import com.example.foxminded_newsfeed.ui.screen.favoriteNews.FavoriteNewsVM
import com.example.foxminded_newsfeed.ui.screen.newsFromSelectedProvider.NewsFromSelectedProviderVM
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ViewModelModule {

    @Provides
    @Singleton
    fun allNewsVMProvider(getNews: GetNews): AllNewsVM {
        return AllNewsVM(getNews)
    }

    @Provides
    @Singleton
    fun favoriteNewsVMProvider(getFavoriteNews: GetFavoriteNews): FavoriteNewsVM {
        return FavoriteNewsVM(getFavoriteNews)
    }

    @Provides
    @Singleton
    fun newsFromSelectedProviderVMProvider(
        getNewsFromSelectedProvider: GetNewsFromSelectedProvider
    ): NewsFromSelectedProviderVM {
        return NewsFromSelectedProviderVM(getNewsFromSelectedProvider)
    }
}