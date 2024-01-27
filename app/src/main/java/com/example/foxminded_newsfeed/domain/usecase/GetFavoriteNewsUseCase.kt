package com.example.foxminded_newsfeed.domain.usecase

import com.example.foxminded_newsfeed.data.repository.NewsRepository
import com.example.foxminded_newsfeed.data.room.NewsEntity
import kotlinx.coroutines.flow.Flow

class GetFavoriteNewsUseCase(private val newsRepository: NewsRepository) {
    suspend fun invoke(): Flow<List<NewsEntity>> {
        return newsRepository.getFavoriteNews()
    }
}