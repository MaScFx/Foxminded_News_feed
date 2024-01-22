package com.example.foxminded_newsfeed.di

import com.example.foxminded_newsfeed.data.repository.NewsRepository
import com.example.foxminded_newsfeed.domain.usecase.ChangeFavoriteStatusUseCase
import com.example.foxminded_newsfeed.domain.usecase.GetFavoriteNewsUseCase
import com.example.foxminded_newsfeed.domain.usecase.GetNewsUseCase
import com.example.foxminded_newsfeed.domain.usecase.GetNewsFromSelectedProviderUseCase
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
    fun getFavoriteNewsProvider(newsRepository: NewsRepository): GetFavoriteNewsUseCase {
        return GetFavoriteNewsUseCase(newsRepository = newsRepository)
    }

    @Provides
    @Singleton
    fun getNewsProvider(newsRepository: NewsRepository): GetNewsUseCase {
        return GetNewsUseCase(newsRepository = newsRepository)
    }

    @Provides
    @Singleton
    fun getNewsFromSelectedProviderProvider(newsRepository: NewsRepository): GetNewsFromSelectedProviderUseCase {
        return GetNewsFromSelectedProviderUseCase(newsRepository = newsRepository)
    }

    @Provides
    @Singleton
    fun getAddNewsToFavoriteProvider(newsRepository: NewsRepository): ChangeFavoriteStatusUseCase {
        return ChangeFavoriteStatusUseCase(newsRepository = newsRepository)
    }
}