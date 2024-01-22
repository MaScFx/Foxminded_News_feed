package com.example.foxminded_newsfeed.data.repository

import com.example.foxminded_newsfeed.domain.model.NewsItem
import com.example.foxminded_newsfeed.domain.model.NewsSource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun checkNewFromSource(newsSource: NewsSource): List<NewsItem>
    suspend fun getNewsFromBD(): List<NewsItem>
    suspend fun saveNewsItemInDB(newsItem: NewsItem)
    suspend fun getFavoriteNews(): Flow<List<NewsItem>>
    suspend fun delete(itemID: String)
    suspend fun deleteAllNotFavoriteItems()
}