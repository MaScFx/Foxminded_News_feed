package com.example.foxminded_newsfeed.data.repository

import com.example.foxminded_newsfeed.data.network.reddit.RedditRetrofitClient
import com.example.foxminded_newsfeed.data.network.welt.WeltRetrofitClient
import com.example.foxminded_newsfeed.data.room.MainDB
import com.example.foxminded_newsfeed.data.room.NewsEntity
import com.example.foxminded_newsfeed.domain.model.NewsSource
import kotlinx.coroutines.flow.Flow
import java.io.IOException
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject


class NewsRepositoryImpl @Inject constructor(
    private val mainDB: MainDB,
    private val weltRetrofitClient: WeltRetrofitClient,
    private val redditRetrofitClient: RedditRetrofitClient
) : NewsRepository {
    private val cacheMap: HashMap<String, NewsEntity> = HashMap()
    private var mainDBIsChange: Boolean = true

    override suspend fun checkNewFromSource(newsSource: NewsSource): List<NewsEntity> {
        val weltFormatter =
            DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
        val resultList: MutableList<NewsEntity> = mutableListOf()
        if (cacheMap.isEmpty() || mainDBIsChange) {
            mainDBIsChange = false
            mainDB.dao.getAllNews().forEach {
                cacheMap[it.id] = it
            }
        }


        try {
            when (newsSource) {
                NewsSource.WELT -> weltRetrofitClient.weltApi.getWeltNews().channel?.item?.map { newsModel ->
                    val isExist = cacheMap[newsModel.id]?.isFavorite ?: 0

                    resultList.add(
                        NewsEntity(
                            imgUrl = newsModel.content[0]?.url
                                ?: "https://edition.welt.de/assets/app_download.png",
                            title = newsModel.title,
                            newsSource = NewsSource.WELT,
                            publishedTime = ZonedDateTime.parse(
                                newsModel.published, weltFormatter
                            ) ?: ZonedDateTime.now(),
                            isFavorite = isExist,
                            link = newsModel.link,
                            id = newsModel.id
                        )
                    )
                }

                NewsSource.Reddit -> redditRetrofitClient.redditApi.getRedditNews().items?.map { newsModel ->
                    val isExist = cacheMap[newsModel.id]?.isFavorite ?: 0

                    resultList.add(
                        NewsEntity(
                            imgUrl = newsModel.imgUrl?.url
                                ?: "https://cdn3.iconfinder.com/data/icons/2018-social-media-logotypes/1000/2018_social_media_popular_app_logo_reddit-512.png",
                            title = newsModel.title,
                            newsSource = NewsSource.Reddit,
                            publishedTime = ZonedDateTime.parse(newsModel.published)
                                ?: ZonedDateTime.now(),
                            isFavorite = isExist,
                            link = newsModel.link.href ?: "no link",
                            id = newsModel.id
                        )
                    )
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return resultList
    }

    override suspend fun getNewsFromBD(): List<NewsEntity> {
        updateCache()
        return cacheMap.values.toList()
    }

    override suspend fun saveNewsItemInDB(newsEntity: NewsEntity) {
        if (cacheMap[newsEntity.id]?.isFavorite != 1) {
            mainDBIsChange = true
            mainDB.dao.insert(newsEntity)
        }
    }

    override suspend fun getFavoriteNews(): Flow<List<NewsEntity>> {
        return mainDB.dao.getFavoriteNews()
    }

    override suspend fun delete(itemID: String) {
        mainDBIsChange = true
        mainDB.dao.delete(itemID)
    }

    private fun updateCache() {
        if (cacheMap.isEmpty() || mainDBIsChange) {
            mainDBIsChange = false
            mainDB.dao.getAllNews().forEach {
                cacheMap[it.id] = it
            }
        }
    }
}