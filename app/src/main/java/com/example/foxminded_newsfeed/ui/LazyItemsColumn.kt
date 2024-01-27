package com.example.foxminded_newsfeed.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.foxminded_newsfeed.data.room.NewsEntity


@Composable
fun LazyItemsColumn(
    listNewsItems: List<NewsEntity>,
    onFavoriteButtonClick: (NewsEntity) -> Unit,
    onItemCLick:(NewsEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val sortedList = listNewsItems.toMutableList()
    sortedList.sortWith { newsItem, newsItem2 ->
        newsItem.publishedTime.compareTo(
            newsItem2.publishedTime
        )
    }
    sortedList.reverse()

    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items(items = sortedList) { news ->
            ItemNews(
                newsItem = news,
                onFavoriteButtonClick = onFavoriteButtonClick,
                onItemCLick = onItemCLick
            )
        }
    }
}