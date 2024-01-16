package com.example.foxminded_newsfeed.domain.usecase

import com.example.foxminded_newsfeed.data.NewsRepository
import com.example.foxminded_newsfeed.domain.model.NewsItem

class ClickFavoriteButtonOnItem(private val newsRepository: NewsRepository) {
    suspend fun cLick(newsItem: NewsItem) {
        // 1 - true, other =false. Room cannot use boolean
        if (newsItem.isFavorites == 1) {
            newsRepository.delete(newsItem.id)
        } else {
            newsRepository.saveNewsItemInDB(newsItem.copy(isFavorites = 1))
        }
    }
}