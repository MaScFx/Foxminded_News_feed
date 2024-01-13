package com.example.foxminded_newsfeed.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.foxminded_newsfeed.domain.model.NewsItem
import com.example.foxminded_newsfeed.domain.model.NewsSource
import com.example.foxminded_newsfeed.data.NewsRepository
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.merge

class GetNews(private val newsRepository: NewsRepository) {

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun get(): List<NewsItem> {

        val redditNews = newsRepository.checkNewFromSource(NewsSource.Reddit).asFlow()
        val weltNews = newsRepository.checkNewFromSource(NewsSource.WELT).asFlow()

        val allNewsList: MutableList<NewsItem> = mutableListOf()

        merge(redditNews, weltNews).collect {
            allNewsList.add(it)
        }

        return allNewsList
    }
}