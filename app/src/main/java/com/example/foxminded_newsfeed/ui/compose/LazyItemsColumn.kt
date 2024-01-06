package com.example.foxminded_newsfeed.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.foxminded_newsfeed.model.Item

@Composable
fun LazyItemsColumn(
    listItems: List<Item>,
    modifier: Modifier = Modifier
) {

    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items(items = listItems) { news ->
            ItemNews(
                imgUrl = news.imgUrl,
                title = news.title,
                newsSource = news.newsSource,
                time = news.time,
                isFavorites = news.isFavorites
            )
        }
    }
}