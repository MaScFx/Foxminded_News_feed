package com.example.foxminded_newsfeed.ui

import com.example.foxminded_newsfeed.data.room.NewsEntity

data class UIState(
    val allNews: List<NewsEntity> = ArrayList(),
    val selectedNews: List<NewsEntity> = ArrayList(),
    val favoriteNews: List<NewsEntity> = ArrayList(),
    val showInternetConnectionError: Boolean = false,
    val isRefreshing : Boolean = true
)

