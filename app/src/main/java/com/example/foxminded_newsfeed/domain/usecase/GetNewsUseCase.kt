package com.example.foxminded_newsfeed.domain.usecase

import com.example.foxminded_newsfeed.data.repository.NewsRepository
import com.example.foxminded_newsfeed.domain.model.NewsItem
import com.example.foxminded_newsfeed.domain.model.NewsSource
import com.example.foxminded_newsfeed.domain.model.UseCaseAnswer
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.merge

class GetNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun invoke(): UseCaseAnswer {

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

        return UseCaseAnswer(
            internetIsAvailable = showInternetConnectionError,
            resultList = allNewsList
        )
    }
}