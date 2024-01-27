package com.example.foxminded_newsfeed.domain.usecase

import com.example.foxminded_newsfeed.data.repository.NewsRepository
import com.example.foxminded_newsfeed.data.room.NewsEntity

class ChangeFavoriteStatusUseCase(private val newsRepository: NewsRepository) {
    suspend fun invoke(newsItem: NewsEntity) {
        // 1 - true, other =false. Room cannot use boolean
        if (newsItem.isFavorite == 1) {
            newsRepository.delete(newsItem.id)
        } else {
            newsRepository.saveNewsItemInDB(newsItem.copy(isFavorite = 1))
        }
    }
}