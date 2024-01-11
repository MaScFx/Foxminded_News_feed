package com.example.foxminded_newsfeed.domain.model

import java.time.ZonedDateTime

data class NewsItem(
    val id:String,
    val link: String,
    val imgUrl: String,
    var title: String,
    val newsSource: NewsSource,
    val publicationTime: ZonedDateTime,
    val isFavorites: Boolean
)
