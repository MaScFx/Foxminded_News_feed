package com.example.foxminded_newsfeed.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.foxminded_newsfeed.domain.model.NewsItem

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LazyItemsColumn(
    listNewsItems: List<NewsItem>,
    onFavoriteButtonClick: (NewsItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val sortedList = listNewsItems.toMutableList()
    sortedList.sortWith { newsItem, newsItem2 ->
        newsItem.publicationTime.compareTo(
            newsItem2.publicationTime
        )
    }
    sortedList.reverse()

    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items(items = sortedList) { news ->
            ItemNews(
                newsItem = news,
                onFavoriteButtonClick = onFavoriteButtonClick
            )
        }
    }
}