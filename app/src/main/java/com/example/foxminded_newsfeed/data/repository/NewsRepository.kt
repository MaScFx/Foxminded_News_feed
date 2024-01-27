package com.example.foxminded_newsfeed.data.repository

import com.example.foxminded_newsfeed.data.room.NewsEntity
import com.example.foxminded_newsfeed.domain.model.NewsSource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun checkNewFromSource(newsSource: NewsSource): List<NewsEntity>
    suspend fun getNewsFromBD(): List<NewsEntity>
    suspend fun saveNewsItemInDB(newsEntity: NewsEntity)
    suspend fun getFavoriteNews(): Flow<List<NewsEntity>>
    suspend fun delete(itemID: String)
}