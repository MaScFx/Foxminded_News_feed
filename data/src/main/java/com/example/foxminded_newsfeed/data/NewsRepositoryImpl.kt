package com.example.foxminded_newsfeed.data

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.foxminded_newsfeed.data.`fun`.getFakeNewsModelList
import com.example.foxminded_newsfeed.data.model.NewsModel
import com.example.foxminded_newsfeed.data.network.reddit.RedditRetrofitClient
import com.example.foxminded_newsfeed.data.network.welt.WeltRetrofitClient
import com.example.foxminded_newsfeed.data.room.App
import com.example.foxminded_newsfeed.data.room.FavoriteNewsEntity
import com.example.foxminded_newsfeed.data.room.MainDB
import com.example.foxminded_newsfeed.domain.model.NewsItem
import com.example.foxminded_newsfeed.domain.model.NewsSource
import com.example.foxminded_newsfeed.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


class NewsRepositoryImpl(val context: Context) : NewsRepository {
//    private val mainDB: MainDB = (context as App).database
    private val mainDB: MainDB = App().database
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun checkNewWeltNews(): List<NewsItem> {
        return wn()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun wn(): List<NewsItem> {
        val weltResponse = WeltRetrofitClient.weltApi.getWeltNews()

        val formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)

        return weltResponse.channel?.item?.map { newsModel ->
            NewsItem(
                imgUrl = newsModel.content[0]?.url
                    ?: "https://edition.welt.de/assets/app_download.png",
                title = newsModel.title, newsSource = NewsSource.WELT,
                publicationTime = ZonedDateTime.parse(newsModel.published, formatter)
                    ?: ZonedDateTime.now(),
                isFavorites = false,
                link = newsModel.link ?: "no link",
                id = newsModel.id
            )
        } ?: listOf()

    }


    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun checkNewRedditNews(): List<NewsItem> {

        return rn()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun rn(): List<NewsItem> {
        val response = RedditRetrofitClient.redditApi.getRedditNews()
        return response.items?.map { newsModel ->
            NewsItem(
                imgUrl = newsModel.imgUrl?.url
                    ?: "https://cdn3.iconfinder.com/data/icons/2018-social-media-logotypes/1000/2018_social_media_popular_app_logo_reddit-512.png",
                title = newsModel.title ?: "",
                newsSource = NewsSource.Reddit,
                publicationTime = ZonedDateTime.parse(newsModel.published) ?: ZonedDateTime.now(),
                isFavorites = false,
                link = newsModel.link?.href ?: "no link",
                id = newsModel.id ?: "no id"
            )
        } ?: listOf()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getNewsFromBD(): Flow<List<NewsItem>> {
//        val roomDB = (context as App).database

        val data = mainDB.dao.getAllFavoriteNewsEntity()
            .map { item ->
                convertFavoriteNewsEntityToNewsItem(item)
            }



//        return convertNewsModelToNewsItem(getFakeNewsModelList())
        return data

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getFavoriteNews(): List<NewsItem> {
        return convertNewsModelToNewsItem(getFakeNewsModelList())

    }

    private fun convertNewsModelToNewsItem(list: List<NewsModel>): List<NewsItem> {
        return list.map { newsModel ->
            NewsItem(
                imgUrl = newsModel.imgUrl,
                title = newsModel.title,
                newsSource = newsModel.newsSource,
                publicationTime = newsModel.publicationTime,
                isFavorites = newsModel.isFavorites,
                link = "no link",
                id = "12321"
            )
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun convertFavoriteNewsEntityToNewsItem(list: List<FavoriteNewsEntity>): List<NewsItem> {
        return list.map { favoriteNewsEntity ->
            NewsItem(
                imgUrl = favoriteNewsEntity.imgUrl,
                title = favoriteNewsEntity.title,
                newsSource = favoriteNewsEntity.newsSource,
                publicationTime = ZonedDateTime.parse(favoriteNewsEntity.publishedTime.toString()),
                isFavorites = true,
                link = favoriteNewsEntity.link,
                id = favoriteNewsEntity.id
            )
        }
    }
}