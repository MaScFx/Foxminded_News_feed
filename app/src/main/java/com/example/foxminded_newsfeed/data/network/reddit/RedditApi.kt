package com.example.foxminded_newsfeed.data.network.reddit

import retrofit2.http.GET

interface RedditApi {
    @GET(".rss")
    suspend fun getRedditNews(): RedditRssResponse
}