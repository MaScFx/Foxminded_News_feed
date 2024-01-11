package com.example.foxminded_newsfeed.data.model

import com.example.foxminded_newsfeed.domain.model.NewsSource
import java.time.ZonedDateTime

data class NewsModel(
    val id:String,
    val link: String,
    val imgUrl: String,
    val title: String,
    val newsSource: NewsSource,
    val publicationTime: ZonedDateTime,
    val isFavorites: Boolean,
)
