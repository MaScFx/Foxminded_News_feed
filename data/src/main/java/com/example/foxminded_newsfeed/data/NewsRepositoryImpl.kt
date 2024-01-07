package com.example.foxminded_newsfeed.data

import com.example.foxminded_newsfeed.data.model.NewsModel
import com.example.foxminded_newsfeed.data.network.RetrofitClient
import com.example.foxminded_newsfeed.domain.model.NewsItem
import com.example.foxminded_newsfeed.domain.model.NewsSource
import com.example.foxminded_newsfeed.domain.repository.NewsRepository

class NewsRepositoryImpl : NewsRepository {
    override suspend fun checkNewNews(): List<NewsItem> {

        val response =RetrofitClient.redditApi.getRedditNews()

        return response.items?.map { newsModel ->
            NewsItem(
                imgUrl = newsModel.imgUrl?.url ?:"https://cpad.ask.fm/865/242/195/-9996995-2020i7r-er5rgsls2hkd5p0/large/_pcfqBsO9Ck.jpg",
                title = newsModel.title ?:"",
                newsSource = NewsSource.Reddit,
                time = newsModel.published ?:"3 min ago",
                isFavorites = false,
                link = newsModel.link?.href ?:"no link",
                id = newsModel.id ?:"no id"
            )
        } ?: listOf<NewsItem>()
    }

    override suspend fun getNewsFromBD(): List<NewsItem> {
        return convertNewsModelToNewsItem(getTestItemsNewsData())

    }

    override suspend fun getFavoriteNews(): List<NewsItem> {
        return convertNewsModelToNewsItem(getTestItemsNewsData())

    }

    override suspend fun getNewsFromSelectedProvider(newsSource: NewsSource): List<NewsItem> {
        return convertNewsModelToNewsItem(getTestItemsNewsData())
    }

    private fun convertNewsModelToNewsItem(list: List<NewsModel>): List<NewsItem> {
        return list.map { newsModel ->
            NewsItem(
                imgUrl = newsModel.imgUrl,
                title = newsModel.title,
                newsSource = newsModel.newsSource,
                time = newsModel.time,
                isFavorites = newsModel.isFavorites,
                link = "no link",
                id = "12321"
            )
        }
    }


}