package com.example.foxminded_newsfeed.domain.usecase

import com.example.foxminded_newsfeed.domain.model.NewsItem
import com.example.foxminded_newsfeed.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteNews(private val newsRepository: NewsRepository) {
    fun get(): Flow<List<NewsItem>> {
        return newsRepository.getNewsFromBD()
    }

}