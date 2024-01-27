package com.example.foxminded_newsfeed.di

import android.content.Context
import com.example.foxminded_newsfeed.utils.NetworkConnectionStatus
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UtilsModule {

    @Provides
    @Singleton
    fun networkConnectionStatusProvider(@ApplicationContext context: Context): NetworkConnectionStatus {
        return NetworkConnectionStatus(context)
    }
}