package com.example.foxminded_newsfeed.data.model

import com.example.foxminded_newsfeed.domain.model.NewsSource

data class NewsModel(
    val id:String,
    val imgUrl: String,
    val title: String,
    val newsSource: NewsSource,
    val time: String,
    val isFavorites: Boolean
)
