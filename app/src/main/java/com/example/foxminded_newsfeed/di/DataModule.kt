package com.example.foxminded_newsfeed.di

import android.content.Context
import com.example.foxminded_newsfeed.data.Converters
import com.example.foxminded_newsfeed.data.NewsRepository
import com.example.foxminded_newsfeed.data.NewsRepositoryImpl
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
        converters: Converters
    ): NewsRepository {
        return NewsRepositoryImpl(
            mainDB = mainDB,
            weltRetrofitClient = weltRetrofitClient,
            redditRetrofitClient = redditRetrofitClient,
            converters = converters
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

    @Provides
    fun provideConverters(@ApplicationContext context: Context): Converters {
        return Converters(context)
    }
}
