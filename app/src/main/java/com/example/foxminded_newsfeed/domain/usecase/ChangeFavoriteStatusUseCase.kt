package com.example.foxminded_newsfeed.domain.usecase

import com.example.foxminded_newsfeed.data.repository.NewsRepository
import com.example.foxminded_newsfeed.domain.model.NewsItem

class ChangeFavoriteStatusUseCase(private val newsRepository: NewsRepository) {
    suspend fun invoke(newsItem: NewsItem) {
        // 1 - true, other =false. Room cannot use boolean
        if (newsItem.isFavorites == 1) {
            newsRepository.delete(newsItem.id)
        } else {
            newsRepository.saveNewsItemInDB(newsItem.copy(isFavorites = 1))
        }
    }
}