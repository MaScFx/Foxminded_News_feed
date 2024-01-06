package com.example.foxminded_newsfeed.data

import com.example.foxminded_newsfeed.domain.model.NewsItem
import com.example.foxminded_newsfeed.domain.model.NewsSource
import com.example.foxminded_newsfeed.domain.repository.NewsRepository

class NewsRepositoryImpl: NewsRepository {
    override fun checkNewNews(): List<NewsItem> {
        return getTestItemsNewsData()
    }

    override fun getNewsFromBD(): List<NewsItem> {
        return getTestItemsNewsData()

    }

    override fun getFavoriteNews(): List<NewsItem> {
        return getTestItemsNewsData()

    }

    override fun getNewsFromSelectedProvider(newsSource: NewsSource): List<NewsItem> {
        return getTestItemsNewsData()
    }


}