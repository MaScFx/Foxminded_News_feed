package com.example.foxminded_newsfeed.ui

import com.example.foxminded_newsfeed.domain.model.NewsItem

data class UIState (
    val allNewsList: List<NewsItem> = ArrayList(),
    val selectedNewsList: List<NewsItem> = ArrayList(),
)
