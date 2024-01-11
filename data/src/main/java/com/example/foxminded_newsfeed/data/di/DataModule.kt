//package com.example.foxminded_newsfeed.data.di
//
//import android.content.Context
//import com.example.foxminded_newsfeed.data.room.App
//import com.example.foxminded_newsfeed.data.room.MainDB
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.components.SingletonComponent
//
//@Module
//@InstallIn(SingletonComponent::class)
//object DataModule {
////    @Provides
////    fun provideRoom(@ApplicationContext context: Context): MainDB {
////        return (context as App).database
////    }
//    @Provides
//    fun provideRoom(): String {
//        return "testString"
//    }
//}