package com.example.foxminded_newsfeed.data

import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.foxminded_newsfeed.data.`fun`.getFakeNewsModelList
import com.example.foxminded_newsfeed.data.network.reddit.RedditRetrofitClient
import com.example.foxminded_newsfeed.data.network.welt.WeltRetrofitClient
import com.example.foxminded_newsfeed.data.room.FavoriteNewsEntity
import com.example.foxminded_newsfeed.data.room.MainDB
import com.example.foxminded_newsfeed.domain.model.NewsItem
import com.example.foxminded_newsfeed.domain.model.NewsSource
import com.example.foxminded_newsfeed.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject
import kotlin.random.Random


class NewsRepositoryImpl @Inject constructor(
    private val mainDB: MainDB,
    private val weltRetrofitClient: WeltRetrofitClient,
    private val redditRetrofitClient: RedditRetrofitClient
) : NewsRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun checkNewFromSource(newsSource: NewsSource): List<NewsItem> {
        val weltFormatter =
            DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
        return when (newsSource) {
            NewsSource.WELT -> weltRetrofitClient.weltApi.getWeltNews().channel?.item?.map { newsModel ->
                NewsItem(
                    imgUrl = newsModel.content[0]?.url
                        ?: "https://edition.welt.de/assets/app_download.png",
                    title = newsModel.title,
                    newsSource = NewsSource.WELT,
                    publicationTime = ZonedDateTime.parse(newsModel.published, weltFormatter)
                        ?: ZonedDateTime.now(),
                    isFavorites = false,
                    link = newsModel.link ?: "no link",
                    id = newsModel.id
                )
            } ?: listOf()

            NewsSource.Reddit -> redditRetrofitClient.redditApi.getRedditNews().items?.map { newsModel ->
                NewsItem(
                    imgUrl = newsModel.imgUrl?.url
                        ?: "https://cdn3.iconfinder.com/data/icons/2018-social-media-logotypes/1000/2018_social_media_popular_app_logo_reddit-512.png",
                    title = newsModel.title ?: "",
                    newsSource = NewsSource.Reddit,
                    publicationTime = ZonedDateTime.parse(newsModel.published)
                        ?: ZonedDateTime.now(),
                    isFavorites = false,
                    link = newsModel.link?.href ?: "no link",
                    id = newsModel.id ?: "no id"
                )
            } ?: listOf()
        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun initTestData() {
        mainDB.dao.insertFavoriteNewsEntity(
            FavoriteNewsEntity(
                id = Random.nextInt(0,9999999).toString(),
                link = "https://cpad.ask.fm/865/242/195/-9996995-2020i7r-er5rgsls2hkd5p0/large/_pcfqBsO9Ck.jpg",
                title = "Title ${Random.nextInt(0,9999999)}",
                img = Bitmap.createBitmap(20, 20, Bitmap.Config.ARGB_8888),
                newsSource = NewsSource.Reddit,
                imgUrl = "https://cpad.ask.fm/865/242/195/-9996995-2020i7r-er5rgsls2hkd5p0/large/_pcfqBsO9Ck.jpg",
                publishedTime = ZonedDateTime.now()
            )
        )
    }




    @RequiresApi(Build.VERSION_CODES.O)
    override fun getNewsFromBD(): Flow<List<NewsItem>> {
        return mainDB.dao.getAllFavoriteNewsEntity().map { item ->

                convertFavoriteNewsEntityToNewsItem(item)
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getFavoriteNews(): List<NewsItem> {
        return convertNewsModelToNewsItem(getFakeNewsModelList())
    }

    private fun convertNewsModelToNewsItem(list: List<NewsItem>): List<NewsItem> {
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
        }.reversed()
    }
}