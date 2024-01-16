package com.example.foxminded_newsfeed.data

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.example.foxminded_newsfeed.data.room.NewsEntity
import com.example.foxminded_newsfeed.domain.model.NewsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Converters @Inject constructor(private val context: Context) {
    suspend fun convertNewsItemToNewsEntity(newsItem: NewsItem): NewsEntity {
        val imgBitmap = withContext(Dispatchers.IO) {
            try {
                Glide.with(context).asBitmap().load(newsItem.imgUrl).submit().get()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        return NewsEntity(
            id = newsItem.id,
            newsSource = newsItem.newsSource,
            link = newsItem.link,
            publishedTime = newsItem.publicationTime,
            title = newsItem.title,
            imgUrl = newsItem.imgUrl,
            img = imgBitmap,
            isFavorite = newsItem.isFavorites
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertNewsEntityListToNewsItemList(list: List<NewsEntity>): List<NewsItem> {
        return list.map { favoriteNewsEntity ->
            NewsItem(
                id = favoriteNewsEntity.id,
                newsSource = favoriteNewsEntity.newsSource,
                link = favoriteNewsEntity.link,
                publicationTime = favoriteNewsEntity.publishedTime,
                title = favoriteNewsEntity.title,
                imgUrl = favoriteNewsEntity.imgUrl,
                isFavorites = favoriteNewsEntity.isFavorite,
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertNewsEntityToNewsItem(newsEntity: NewsEntity): NewsItem {
        return NewsItem(
            id = newsEntity.id,
            newsSource = newsEntity.newsSource,
            link = newsEntity.link,
            publicationTime = newsEntity.publishedTime,
            title = newsEntity.title,
            imgUrl = newsEntity.imgUrl,
            isFavorites = newsEntity.isFavorite,
        )
    }
}


