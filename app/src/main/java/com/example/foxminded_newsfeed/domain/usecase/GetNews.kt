package com.example.foxminded_newsfeed.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.foxminded_newsfeed.domain.model.NewsItem
import com.example.foxminded_newsfeed.domain.model.NewsSource
import com.example.foxminded_newsfeed.data.NewsRepository
import com.example.foxminded_newsfeed.domain.model.UseCaseAnswer
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.merge

class GetNews(private val newsRepository: NewsRepository) {

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun get(): UseCaseAnswer {

        val redditNews = newsRepository.checkNewFromSource(NewsSource.Reddit).asFlow()
        val weltNews = newsRepository.checkNewFromSource(NewsSource.WELT).asFlow()

        val allNewsList: MutableList<NewsItem> = mutableListOf()

        merge(redditNews, weltNews).collect {
            allNewsList.add(it)
        }
        var showInternetConnectionError = false
        if (allNewsList.size == 0) {
            allNewsList.addAll(newsRepository.getNewsFromBD())
            showInternetConnectionError = true
        } else {
            newsRepository.deleteAllNotFavoriteItems()
            allNewsList.forEach {
                newsRepository.saveNewsItemInDB(it)
            }
        }

        return UseCaseAnswer(internetIsAvailable = showInternetConnectionError, resultList = allNewsList)
    }
}