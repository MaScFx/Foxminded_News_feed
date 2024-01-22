package com.example.foxminded_newsfeed.data.room

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.graphics.createBitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.foxminded_newsfeed.domain.model.NewsItem
import com.example.foxminded_newsfeed.domain.model.NewsSource
import java.io.ByteArrayOutputStream
import java.time.ZonedDateTime

@Entity
data class NewsEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    val link: String,
    val imgUrl: String,
    var img: Bitmap? = null,
    val title: String,
    val newsSource: NewsSource,
    val publishedTime: ZonedDateTime,
    val isFavorite: Int
    // 1 - true, other =false. Room cannot use boolean
) {
    companion object {
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
}

class ZonedDateTimeConverter {
    @TypeConverter
    fun zonedDateTimeToString(time: ZonedDateTime): String {
        return time.toString()
    }

    @TypeConverter
    fun stringToZonedDateTime(time: String): ZonedDateTime {
        return ZonedDateTime.parse(time)
    }

    @TypeConverter
    fun fromBitmap(bmp: Bitmap?): ByteArray? {
        return try {
            if (bmp != null) {
                val outputStream = ByteArrayOutputStream()
                bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.toByteArray()
            } else {
                ByteArray(0)
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            ByteArray(0)
        }
    }

    @TypeConverter
    fun toBitmap(bytes: ByteArray?): Bitmap {
        return if (bytes != null) {
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        } else {
            createBitmap(1, 1)
        }
    }
}