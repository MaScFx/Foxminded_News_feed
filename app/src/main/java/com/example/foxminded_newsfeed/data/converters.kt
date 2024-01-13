package com.example.foxminded_newsfeed.data

import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.foxminded_newsfeed.data.room.NewsEntity
import com.example.foxminded_newsfeed.domain.model.NewsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL
import java.time.ZonedDateTime

suspend fun convertNewsItemToNewsEntity(newsItem: NewsItem): NewsEntity {

    val img = withContext(Dispatchers.IO) {
        BitmapFactory.decodeStream(URL(newsItem.imgUrl).openStream())
    }
    return NewsEntity(
        id = newsItem.id,
        newsSource = newsItem.newsSource,
        link = newsItem.link,
        publishedTime = newsItem.publicationTime,
        title = newsItem.title,
        imgUrl = newsItem.imgUrl,
        img = img,
        isFavorite = newsItem.isFavorites
    )

}

@RequiresApi(Build.VERSION_CODES.O)
fun convertNewsEntityToNewsItem(list: List<NewsEntity>): List<NewsItem> {
    return list.map { favoriteNewsEntity ->
        NewsItem(
            id = favoriteNewsEntity.id,
            newsSource = favoriteNewsEntity.newsSource,
            link = favoriteNewsEntity.link,
            publicationTime = ZonedDateTime.parse(favoriteNewsEntity.publishedTime.toString()),
            title = favoriteNewsEntity.title,
            imgUrl = favoriteNewsEntity.imgUrl,
            isFavorites = favoriteNewsEntity.isFavorite,
        )
    }.reversed()
}

fun convertNewsModelToNewsItems(list: List<NewsItem>): List<NewsItem> {
    return list.map { newsModel ->
        NewsItem(
            imgUrl = newsModel.imgUrl,
            title = newsModel.title,
            newsSource = newsModel.newsSource,
            publicationTime = newsModel.publicationTime,
            isFavorites = newsModel.isFavorites,
            link = newsModel.link,
            id = newsModel.id
        )
    }
}