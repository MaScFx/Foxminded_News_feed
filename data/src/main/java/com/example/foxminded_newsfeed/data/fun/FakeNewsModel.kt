package com.example.foxminded_newsfeed.data.`fun`

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.foxminded_newsfeed.data.model.NewsModel
import com.example.foxminded_newsfeed.domain.model.NewsSource
import java.time.Clock
import java.time.ZoneId
import java.time.ZonedDateTime

@RequiresApi(Build.VERSION_CODES.O)
fun getFakeNewsModelList(): List<NewsModel> {
    val list = ArrayList<NewsModel>()
    for (i in 0 until 30) {
        list.add(
            NewsModel(
                isFavorites = false,
                publicationTime = ZonedDateTime.now(),
                newsSource = NewsSource.Reddit,
                title = "$i Good news!! Your DOG Win five million dollars! Graz!",
                imgUrl = "https://cpad.ask.fm/865/242/195/-9996995-2020i7r-er5rgsls2hkd5p0/large/_pcfqBsO9Ck.jpg",
                id = "12312",
                link = "https:/reddit.com"
            )
        )
    }
    return list
}