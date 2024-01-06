package com.example.foxminded_newsfeed.domain.model

data class NewsItem(
    val imgUrl: String,
    var title: String,
    val newsSource: NewsSource,
    val time: String,
    val isFavorites: Boolean
)
