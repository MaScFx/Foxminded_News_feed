package com.example.foxminded_newsfeed.utils

import com.example.foxminded_newsfeed.domain.model.NewsItem
import com.example.foxminded_newsfeed.domain.model.NewsSource
import java.time.ZonedDateTime

fun getFakeNewsItems():List<NewsItem> {
    val list = ArrayList<NewsItem>()
    for (i in 0 until 30) {
        list.add(
            NewsItem(
                isFavorites = 0,
                publicationTime = ZonedDateTime.now(),
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

