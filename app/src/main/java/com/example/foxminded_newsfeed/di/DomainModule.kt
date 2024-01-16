package com.example.foxminded_newsfeed.di

import com.example.foxminded_newsfeed.data.NewsRepository
import com.example.foxminded_newsfeed.domain.usecase.ClickFavoriteButtonOnItem
import com.example.foxminded_newsfeed.domain.usecase.GetFavoriteNews
import com.example.foxminded_newsfeed.domain.usecase.GetNews
import com.example.foxminded_newsfeed.domain.usecase.GetNewsFromSelectedProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    @Singleton
    fun getFavoriteNewsProvider(newsRepository: NewsRepository): GetFavoriteNews {
        return GetFavoriteNews(newsRepository = newsRepository)
    }

    @Provides
    @Singleton
    fun getNewsProvider(newsRepository: NewsRepository): GetNews {
        return GetNews(newsRepository = newsRepository)
    }

    @Provides
    @Singleton
    fun getNewsFromSelectedProviderProvider(newsRepository: NewsRepository): GetNewsFromSelectedProvider {
        return GetNewsFromSelectedProvider(newsRepository = newsRepository)
    }

    @Provides
    @Singleton
    fun getAddNewsToFavoriteProvider(newsRepository: NewsRepository): ClickFavoriteButtonOnItem {
        return ClickFavoriteButtonOnItem(newsRepository = newsRepository)
    }
}