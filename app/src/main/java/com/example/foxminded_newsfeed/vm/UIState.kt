package com.example.foxminded_newsfeed.vm

import com.example.foxminded_newsfeed.domain.model.NewsItem

data class UIState (
    val newsList: List<NewsItem> = ArrayList()
)
