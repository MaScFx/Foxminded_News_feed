package com.example.foxminded_newsfeed.ui.compose

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
    modifier: Modifier = Modifier
) {

    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items(items = listNewsItems) { news ->
            ItemNews(
                imgUrl = news.imgUrl,
                title = news.title,
                newsSource = news.newsSource,
                publishedTime = news.publicationTime,
                isFavorites = news.isFavorites
            )
        }
    }
}