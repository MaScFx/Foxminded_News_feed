package com.example.foxminded_newsfeed.domain.usecase

import com.example.foxminded_newsfeed.data.repository.NewsRepository
import com.example.foxminded_newsfeed.domain.model.NewsItem
import com.example.foxminded_newsfeed.domain.model.NewsSource

class GetNewsFromSelectedProviderUseCase(private val newsRepository: NewsRepository) {
    suspend fun invoke(newsSource: NewsSource): List<NewsItem> {
        return when (newsSource) {
            NewsSource.Reddit -> newsRepository.checkNewFromSource(NewsSource.Reddit)
            NewsSource.WELT -> newsRepository.checkNewFromSource(NewsSource.WELT)
        }
    }
}