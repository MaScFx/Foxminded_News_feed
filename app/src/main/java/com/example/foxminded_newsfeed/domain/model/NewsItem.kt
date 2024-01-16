package com.example.foxminded_newsfeed.domain.model

import android.os.Build
import androidx.annotation.RequiresApi
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
)
