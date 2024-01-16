package com.example.foxminded_newsfeed.domain.usecase

import com.example.foxminded_newsfeed.domain.model.NewsItem
import com.example.foxminded_newsfeed.data.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteNews(private val newsRepository: NewsRepository) {
    suspend fun get(): Flow<List<NewsItem>> {
        return newsRepository.getFavoriteNews()
    }
}