package com.example.foxminded_newsfeed.domain.usecase

import com.example.foxminded_newsfeed.domain.model.NewsItem
import com.example.foxminded_newsfeed.domain.model.NewsSource
import com.example.foxminded_newsfeed.domain.repository.NewsRepository

class GetNewsFromSelectedProvider(private val newsRepository: NewsRepository) {
    //private val newsSource: NewsSource
    fun get(newsSource: NewsSource): List<NewsItem> {
        return newsRepository.getNewsFromSelectedProvider(newsSource)
    }
}