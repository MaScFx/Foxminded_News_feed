package com.example.foxminded_newsfeed.di

import android.content.Context
import com.example.foxminded_newsfeed.data.repository.NewsRepository
import com.example.foxminded_newsfeed.data.repository.NewsRepositoryImpl
import com.example.foxminded_newsfeed.data.network.reddit.RedditRetrofitClient
import com.example.foxminded_newsfeed.data.network.welt.WeltRetrofitClient
import com.example.foxminded_newsfeed.data.room.MainDB
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
        redditRetrofitClient: RedditRetrofitClient,
        @ApplicationContext context: Context
    ): NewsRepository {
        return NewsRepositoryImpl(
            mainDB = mainDB,
            weltRetrofitClient = weltRetrofitClient,
            redditRetrofitClient = redditRetrofitClient,
            context = context
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
