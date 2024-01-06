package com.example.foxminded_newsfeed.data

import com.example.foxminded_newsfeed.domain.model.NewsItem
import com.example.foxminded_newsfeed.domain.model.NewsSource

fun getTestItemsNewsData(): List<NewsItem> {
    val list = ArrayList<NewsItem>()
    for (i in 0 until 30) {
        list.add(
            NewsItem(
                isFavorites = false,
                time = "$i minutes ago",
                newsSource = NewsSource.BBC,
                title = "$i Good news!! Your DOG Win five million dollars! Graz!",
                imgUrl = "https://cpad.ask.fm/865/242/195/-9996995-2020i7r-er5rgsls2hkd5p0/large/_pcfqBsO9Ck.jpg",
            )
        )
    }
    return list
}