package com.example.foxminded_newsfeed.data

import com.example.foxminded_newsfeed.data.model.NewsModel
import com.example.foxminded_newsfeed.data.network.reddit.RedditRetrofitClient
import com.example.foxminded_newsfeed.data.network.welt.WeltRetrofitClient
import com.example.foxminded_newsfeed.domain.model.NewsItem
import com.example.foxminded_newsfeed.domain.model.NewsSource
import com.example.foxminded_newsfeed.domain.repository.NewsRepository

class NewsRepositoryImpl : NewsRepository {
    override suspend fun checkNewNews(): List<NewsItem> {

//        val response = RedditRetrofitClient.redditApi.getRedditNews()
//
//        val RedditResponse = response.items?.map { newsModel ->
//            NewsItem(
//                imgUrl = newsModel.imgUrl?.url
//                    ?: "https://cdn3.iconfinder.com/data/icons/2018-social-media-logotypes/1000/2018_social_media_popular_app_logo_reddit-512.png",
//                title = newsModel.title ?: "",
//                newsSource = NewsSource.Reddit,
//                time = newsModel.published ?: "3 min ago",
//                isFavorites = false,
//                link = newsModel.link?.href ?: "no link",
//                id = newsModel.id ?: "no id"
//            )
//        } ?: listOf<NewsItem>()

        val weltResponse = WeltRetrofitClient.weltApi.getWeltNews()

        return weltResponse.channel?.item?.map { newsModel ->
            NewsItem(
                imgUrl = newsModel.content[0]?.url ?: "https://edition.welt.de/assets/app_download.png",
//                imgUrl = "https://edition.welt.de/assets/app_download.png",
                title = newsModel.title,
                newsSource = NewsSource.WELT,
//                time = "3 min ago",
                time = newsModel.published ?: "3 min ago",
                isFavorites = false,
//                link = "no link",
                link = newsModel.link ?: "no link",
//                id = "no id"
                id = newsModel.id ?: "no id"
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