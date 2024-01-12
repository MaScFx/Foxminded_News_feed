package com.example.foxminded_newsfeed.domain.repository

import com.example.foxminded_newsfeed.domain.model.NewsItem
import com.example.foxminded_newsfeed.domain.model.NewsSource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun checkNewFromSource(newsSource: NewsSource): List<NewsItem>
    fun getNewsFromBD(): Flow<List<NewsItem>>
    suspend fun getFavoriteNews(): List<NewsItem>
    suspend fun initTestData()
}