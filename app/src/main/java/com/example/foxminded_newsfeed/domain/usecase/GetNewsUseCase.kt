package com.example.foxminded_newsfeed.domain.usecase

import com.example.foxminded_newsfeed.data.repository.NewsRepository
import com.example.foxminded_newsfeed.data.room.NewsEntity
import com.example.foxminded_newsfeed.domain.model.GetNewsAnswer
import com.example.foxminded_newsfeed.domain.model.NewsSource
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GetNewsUseCase(private val newsRepository: NewsRepository) {

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun invoke(): GetNewsAnswer {
        val allNewsList: MutableList<NewsEntity> = mutableListOf()
        allNewsList.addAll(newsRepository.checkNewFromSource(NewsSource.Reddit))
        allNewsList.addAll(newsRepository.checkNewFromSource(NewsSource.WELT))

        var showInternetConnectionError = false

        if (allNewsList.size == 0) {
            allNewsList.addAll(newsRepository.getNewsFromBD())
            showInternetConnectionError = true
        } else {
            allNewsList.forEach {
                GlobalScope.launch(Dispatchers.IO) {
                    newsRepository.saveNewsItemInDB(it)
                }
            }
        }

        return GetNewsAnswer(
            internetIsAvailable = showInternetConnectionError, resultList = allNewsList
        )
    }
}