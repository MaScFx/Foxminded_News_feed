package com.example.foxminded_newsfeed.data.network

import retrofit2.http.GET

interface RedditApi {
    @GET(".rss")
    suspend fun getRedditNews(): RedditRssResponse
}