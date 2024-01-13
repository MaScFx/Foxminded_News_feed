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
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject


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
                val isExistBoolean = withContext(Dispatchers.IO) {
                    mainDB.dao.getByID(newsModel.id)?.isFavorite ?: 0
                }

                NewsItem(
                    imgUrl = newsModel.content[0]?.url
                        ?: "https://edition.welt.de/assets/app_download.png",
                    title = newsModel.title,
                    newsSource = NewsSource.WELT,
                    publicationTime = ZonedDateTime.parse(newsModel.published, weltFormatter)
                        ?: ZonedDateTime.now(),
                    isFavorites = isExistBoolean,
                    link = newsModel.link,
                    id = newsModel.id
                )
            } ?: listOf()

            NewsSource.Reddit -> redditRetrofitClient.redditApi.getRedditNews().items?.map { newsModel ->
                val isExist = withContext(Dispatchers.IO) {
                    mainDB.dao.getByID(newsModel.id)?.isFavorite ?: 0
                }

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
            } ?: listOf()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getNewsFromBD(): Flow<List<NewsItem>> {
        return mainDB.dao.getAllNews().map { item ->
            convertNewsEntityToNewsItem(item)
        }
    }

    override suspend fun saveNewsItemInDB(newsItem: NewsItem) {
        val favoriteNewsEntity = convertNewsItemToNewsEntity(newsItem)
        mainDB.dao.insert(favoriteNewsEntity)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getFavoriteNews(): Flow<List<NewsItem>> {
        return mainDB.dao.getFavoriteNews().map { item ->
            convertNewsEntityToNewsItem(item)
        }
    }

    override suspend fun delete(itemID: String) {
        mainDB.dao.delete(itemID)
    }


}




//    @RequiresApi(Build.VERSION_CODES.O)
//    override suspend fun initTestData() {
//        mainDB.dao.insert(
//            NewsEntity(
//                id = Random.nextInt(0, 9999999).toString(),
//                link = "https://cpad.ask.fm/865/242/195/-9996995-2020i7r-er5rgsls2hkd5p0/large/_pcfqBsO9Ck.jpg",
//                title = "Title ${Random.nextInt(0, 9999999)}",
//                img = Bitmap.createBitmap(20, 20, Bitmap.Config.ARGB_8888),
//                newsSource = NewsSource.Reddit,
//                imgUrl = "https://cpad.ask.fm/865/242/195/-9996995-2020i7r-er5rgsls2hkd5p0/large/_pcfqBsO9Ck.jpg",
//                publishedTime = ZonedDateTime.now(),
//                isFavorite = 0
//            )
//        )
//    }