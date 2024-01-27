package com.example.foxminded_newsfeed.utils

import com.example.foxminded_newsfeed.data.room.NewsEntity
import com.example.foxminded_newsfeed.domain.model.NewsSource
import java.time.ZonedDateTime

fun getFakeNewsItems():List<NewsEntity> {
    val list = ArrayList<NewsEntity>()
    for (i in 0 until 30) {
        list.add(
            NewsEntity(
                isFavorite = 0,
                publishedTime = ZonedDateTime.now(),
                newsSource = NewsSource.Reddit,
                title = "$i Good news!! Your DOG Win five million dollars! Graz!",
                imgUrl = "",
                id = "",
                link = ""
            )
        )
    }

    return list
}
