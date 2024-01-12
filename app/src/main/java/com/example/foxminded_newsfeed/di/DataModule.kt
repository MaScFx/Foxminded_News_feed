package com.example.foxminded_newsfeed.di

import android.content.Context
import com.example.foxminded_newsfeed.data.NewsRepositoryImpl
import com.example.foxminded_newsfeed.data.network.reddit.RedditRetrofitClient
import com.example.foxminded_newsfeed.data.network.welt.WeltRetrofitClient
import com.example.foxminded_newsfeed.data.room.MainDB
import com.example.foxminded_newsfeed.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {


    @Provides
    @Singleton
    fun newsRepositoryProvider(
        mainDB: MainDB,
        weltRetrofitClient: WeltRetrofitClient,
        redditRetrofitClient: RedditRetrofitClient
    ): NewsRepository {
        return NewsRepositoryImpl(
            mainDB = mainDB,
            weltRetrofitClient = weltRetrofitClient,
            redditRetrofitClient = redditRetrofitClient
        )
    }

    @Provides
    @Singleton
    fun provideRoomBD(@ApplicationContext context: Context): MainDB {
        return MainDB.getDataBase(context)
    }

    @Provides
    @Singleton
    fun provideWeltRetrofitClient(): WeltRetrofitClient {
        return WeltRetrofitClient()
    }

    @Provides
    @Singleton
    fun provideRedditRetrofitClient(): RedditRetrofitClient {
        return RedditRetrofitClient()
    }
}
