package com.example.foxminded_newsfeed.di

import com.example.foxminded_newsfeed.data.NewsRepositoryImpl
import com.example.foxminded_newsfeed.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {


    @Provides
    @Singleton
    fun newsRepositoryProvider(): NewsRepository {
        return NewsRepositoryImpl()
    }
}
