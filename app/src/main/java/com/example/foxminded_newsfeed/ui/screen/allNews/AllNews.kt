package com.example.foxminded_newsfeed.ui.screen.allNews

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.foxminded_newsfeed.domain.model.NewsItem
import com.example.foxminded_newsfeed.ui.LazyItemsColumn

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AllNews(allNewsVM: AllNewsVM) {
    val newsList by allNewsVM.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LazyItemsColumn(listNewsItems = newsList.allNewsList,
        onFavoriteButtonClick = { allNewsVM.clickFavoriteButton(it) },
        onItemCLick = { openChromeCustomTabs(newsItem = it, context = context) })
}

fun openChromeCustomTabs(newsItem: NewsItem, context: Context) {
    val intent: CustomTabsIntent = CustomTabsIntent.Builder()
        .build()
    intent.launchUrl(context, Uri.parse(newsItem.link))

}