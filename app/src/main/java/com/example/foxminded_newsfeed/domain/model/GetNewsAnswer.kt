package com.example.foxminded_newsfeed.domain.model

data class GetNewsAnswer(val internetIsAvailable: Boolean, val resultList: List<NewsItem>)
