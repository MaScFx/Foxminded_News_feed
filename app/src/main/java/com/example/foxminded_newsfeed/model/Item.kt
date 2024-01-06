package com.example.foxminded_newsfeed.model

import com.example.foxminded_newsfeed.model.NewsSource

data class Item(
    val imgUrl: String,
    val title: String,
    val newsSource: NewsSource,
    val time: String,
    val isFavorites: Boolean
)
