package com.example.foxminded_newsfeed.domain.usecase

import com.example.foxminded_newsfeed.data.NewsRepository
import com.example.foxminded_newsfeed.domain.model.NewsItem
import com.example.foxminded_newsfeed.domain.model.NewsSource

class GetNewsFromSelectedProvider(private val newsRepository: NewsRepository) {
    suspend fun get(newsSource: NewsSource): List<NewsItem> {
        return when (newsSource) {
            NewsSource.Reddit -> newsRepository.checkNewFromSource(NewsSource.Reddit)
            NewsSource.WELT -> newsRepository.checkNewFromSource(NewsSource.WELT)
        }
    }
}