package com.example.foxminded_newsfeed.domain.model

import android.content.Context
import com.bumptech.glide.Glide
import com.example.foxminded_newsfeed.data.room.NewsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime

data class NewsItem(
    val id: String,
    val link: String,
    val imgUrl: String,
    var title: String,
    val newsSource: NewsSource,
    val publicationTime: ZonedDateTime,
    val isFavorites: Int
    // 1 - true, other =false. Room cannot use boolean
){
    companion object{
        suspend fun convertNewsItemToNewsEntity(newsItem: NewsItem, context: Context): NewsEntity {
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
    }
}
