package com.example.foxminded_newsfeed.domain.usecase

import com.example.foxminded_newsfeed.data.repository.NewsRepository
import com.example.foxminded_newsfeed.domain.model.NewsItem
import kotlinx.coroutines.flow.Flow

class GetFavoriteNewsUseCase(private val newsRepository: NewsRepository) {
    suspend fun invoke(): Flow<List<NewsItem>> {
        return newsRepository.getFavoriteNews()
    }
}