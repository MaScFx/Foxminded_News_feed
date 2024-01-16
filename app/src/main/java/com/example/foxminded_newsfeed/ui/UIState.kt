package com.example.foxminded_newsfeed.ui

import com.example.foxminded_newsfeed.domain.model.NewsItem

data class UIState(
    val allNews: List<NewsItem> = ArrayList(),
    val selectedNews: List<NewsItem> = ArrayList(),
    val favoriteNews: List<NewsItem> = ArrayList(),
    val showInternetConnectionError: Boolean = false,
    val isRefreshing : Boolean = true
)
