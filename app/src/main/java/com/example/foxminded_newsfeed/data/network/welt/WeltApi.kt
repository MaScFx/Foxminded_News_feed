package com.example.foxminded_newsfeed.data.network.welt

import retrofit2.http.GET

interface WeltApi {
    @GET("feeds/topnews.rss")
    suspend fun getWeltNews(): Rss
}