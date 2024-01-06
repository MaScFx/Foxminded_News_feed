package com.example.foxminded_newsfeed.domain.repository

import com.example.foxminded_newsfeed.domain.model.NewsItem
import com.example.foxminded_newsfeed.domain.model.NewsSource

interface NewsRepository {
    fun checkNewNews(): List<NewsItem>
    fun getNewsFromBD(): List<NewsItem>
    fun getFavoriteNews(): List<NewsItem>
    fun getNewsFromSelectedProvider(newsSource: NewsSource): List<NewsItem>
}