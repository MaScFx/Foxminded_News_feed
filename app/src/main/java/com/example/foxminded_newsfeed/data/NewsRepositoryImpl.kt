package com.example.foxminded_newsfeed.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.foxminded_newsfeed.data.network.reddit.RedditRetrofitClient
import com.example.foxminded_newsfeed.data.network.welt.WeltRetrofitClient
import com.example.foxminded_newsfeed.data.room.MainDB
import com.example.foxminded_newsfeed.domain.model.NewsItem
import com.example.foxminded_newsfeed.domain.model.NewsSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject


class NewsRepositoryImpl @Inject constructor(
    private val mainDB: MainDB,
    private val weltRetrofitClient: WeltRetrofitClient,
    private val redditRetrofitClient: RedditRetrofitClient,
    private val converters: Converters
) : NewsRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun checkNewFromSource(newsSource: NewsSource): List<NewsItem> {
        val weltFormatter =
            DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
        val resultList: MutableList<NewsItem> = mutableListOf()
        try {
            when (newsSource) {
                NewsSource.WELT -> weltRetrofitClient.weltApi.getWeltNews().channel?.item?.map { newsModel ->
                    val isExistBoolean = withContext(Dispatchers.IO) {
                        mainDB.dao.getByID(newsModel.id)?.isFavorite ?: 0
                    }
                    resultList.add(
                        NewsItem(
                            imgUrl = newsModel.content[0]?.url
                                ?: "https://edition.welt.de/assets/app_download.png",
                            title = newsModel.title,
                            newsSource = NewsSource.WELT,
                            publicationTime = ZonedDateTime.parse(
                                newsModel.published, weltFormatter
                            ) ?: ZonedDateTime.now(),
                            isFavorites = isExistBoolean,
                            link = newsModel.link,
                            id = newsModel.id
                        )
                    )
                }

                NewsSource.Reddit -> redditRetrofitClient.redditApi.getRedditNews().items?.map { newsModel ->
                    val isExist = withContext(Dispatchers.IO) {
                        mainDB.dao.getByID(newsModel.id)?.isFavorite ?: 0
                    }
                    resultList.add(
                        NewsItem(
                            imgUrl = newsModel.imgUrl?.url
                                ?: "https://cdn3.iconfinder.com/data/icons/2018-social-media-logotypes/1000/2018_social_media_popular_app_logo_reddit-512.png",
                            title = newsModel.title,
                            newsSource = NewsSource.Reddit,
                            publicationTime = ZonedDateTime.parse(newsModel.published)
                                ?: ZonedDateTime.now(),
                            isFavorites = isExist,
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

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getNewsFromBD(): List<NewsItem> {
        return withContext(Dispatchers.IO) {
            mainDB.dao.getAllNews().map { item ->
                converters.convertNewsEntityToNewsItem(item)
            }
        }
    }

    override suspend fun saveNewsItemInDB(newsItem: NewsItem) {
        withContext(Dispatchers.IO) {
            val favoriteNewsEntity = converters.convertNewsItemToNewsEntity(newsItem)
            if (mainDB.dao.getByID(newsItem.id)?.isFavorite != 1) {
                mainDB.dao.insert(favoriteNewsEntity)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getFavoriteNews(): Flow<List<NewsItem>> {
        return mainDB.dao.getFavoriteNews().map { item ->
            converters.convertNewsEntityListToNewsItemList(item)
        }
    }

    override suspend fun delete(itemID: String) {
        mainDB.dao.delete(itemID)
    }

    override suspend fun deleteAllNotFavoriteItems() {
        mainDB.dao.deleteAllNotFavoriteItems()
    }
}
