package com.example.foxminded_newsfeed.domain.usecase

import com.example.foxminded_newsfeed.domain.model.NewsItem
import com.example.foxminded_newsfeed.data.NewsRepository

class ClickFavoriteButtonOnItem(private val newsRepository: NewsRepository) {
    suspend fun cLick(newsItem: NewsItem) {
        if (newsItem.isFavorites == 1) {
            newsRepository.delete(newsItem.id)
        } else {
            newsRepository.saveNewsItemInDB(newsItem.copy(isFavorites = 1))
        }

    }
}