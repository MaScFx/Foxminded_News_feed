package com.example.foxminded_newsfeed.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.foxminded_newsfeed.domain.model.NewsSource
import java.time.ZonedDateTime

@Entity
data class NewsEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    val link: String,
    val imgUrl: String,
    val title: String,
    val newsSource: NewsSource,
    val publishedTime: ZonedDateTime,
    val isFavorite: Int
    // 1 - true, other =false. Room cannot use boolean
)

class ZonedDateTimeConverter {
    @TypeConverter
    fun zonedDateTimeToString(time: ZonedDateTime): String {
        return time.toString()
    }

    @TypeConverter
    fun stringToZonedDateTime(time: String): ZonedDateTime {
        return ZonedDateTime.parse(time)
    }
}