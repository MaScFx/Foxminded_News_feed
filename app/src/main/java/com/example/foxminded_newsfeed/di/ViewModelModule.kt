package com.example.foxminded_newsfeed.di

import com.example.foxminded_newsfeed.domain.usecase.GetFavoriteNews
import com.example.foxminded_newsfeed.domain.usecase.GetNews
import com.example.foxminded_newsfeed.domain.usecase.GetNewsFromSelectedProvider
import com.example.foxminded_newsfeed.vm.AllNewsVM
import com.example.foxminded_newsfeed.vm.FavoriteNewsVM
import com.example.foxminded_newsfeed.vm.NewsFromSelectedProviderVM
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