package com.example.foxminded_newsfeed.domain.repository

import com.example.foxminded_newsfeed.domain.model.NewsItem
import com.example.foxminded_newsfeed.domain.model.NewsSource

interface NewsRepository {
    suspend fun checkNewNews(): List<NewsItem>
    suspend fun getNewsFromBD(): List<NewsItem>
    suspend fun getFavoriteNews(): List<NewsItem>
    suspend fun getNewsFromSelectedProvider(newsSource: NewsSource): List<NewsItem>
}