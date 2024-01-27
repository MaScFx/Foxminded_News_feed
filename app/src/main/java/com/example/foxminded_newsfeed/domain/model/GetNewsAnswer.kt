package com.example.foxminded_newsfeed.domain.model

import com.example.foxminded_newsfeed.data.room.NewsEntity


data class GetNewsAnswer(val internetIsAvailable: Boolean, val resultList: List<NewsEntity>)
