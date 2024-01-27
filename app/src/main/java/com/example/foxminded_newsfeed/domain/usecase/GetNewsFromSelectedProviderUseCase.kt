package com.example.foxminded_newsfeed.domain.usecase

import com.example.foxminded_newsfeed.data.repository.NewsRepository
import com.example.foxminded_newsfeed.data.room.NewsEntity
import com.example.foxminded_newsfeed.domain.model.NewsSource

class GetNewsFromSelectedProviderUseCase(private val newsRepository: NewsRepository) {
    suspend fun invoke(newsSource: NewsSource): List<NewsEntity> {
        return newsRepository.checkNewFromSource(newsSource)
    }
}