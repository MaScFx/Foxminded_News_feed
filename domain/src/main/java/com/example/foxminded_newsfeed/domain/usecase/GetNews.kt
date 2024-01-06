package com.example.foxminded_newsfeed.domain.usecase

import com.example.foxminded_newsfeed.domain.model.NewsItem
import com.example.foxminded_newsfeed.domain.repository.NewsRepository

class GetNews(private val newsRepository : NewsRepository) {

    fun get(): List<NewsItem>{
        return newsRepository.checkNewNews()
    }
}