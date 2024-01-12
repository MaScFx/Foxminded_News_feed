package com.example.foxminded_newsfeed.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.foxminded_newsfeed.domain.model.NewsItem
import com.example.foxminded_newsfeed.domain.model.NewsSource
import com.example.foxminded_newsfeed.domain.repository.NewsRepository
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.merge

class GetNews(private val newsRepository: NewsRepository) {

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun get(): List<NewsItem> {
        val redditNews = newsRepository.checkNewFromSource(NewsSource.Reddit).asFlow()
        val weltNews = newsRepository.checkNewFromSource(NewsSource.WELT).asFlow()
        val allNewsList: MutableList<NewsItem> = mutableListOf()
        val allNews = merge(redditNews, weltNews)
        allNews.collect {
            allNewsList.add(it)
        }
        allNewsList.sortWith { newsItem, newsItem2 ->
            newsItem.publicationTime.compareTo(
                newsItem2.publicationTime
            )
        }
        allNewsList.reverse()
        return allNewsList
    }
}