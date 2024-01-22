package com.example.foxminded_newsfeed.ui.screen.favoriteNews

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.foxminded_newsfeed.domain.model.NewsItem
import com.example.foxminded_newsfeed.ui.LazyItemsColumn

@Composable
fun FavoriteNews(favoriteNewsVM: FavoriteNewsVM) {
    val uiState by favoriteNewsVM.uiState.collectAsState()
    val context = LocalContext.current

    LazyItemsColumn(listNewsItems = uiState.favoriteNews,
        onFavoriteButtonClick = { favoriteNewsVM.clickFavoriteButton(it) },
        onItemCLick = { openChromeCustomTabs(newsItem = it, context = context) })
}

fun openChromeCustomTabs(newsItem: NewsItem, context: Context) {
    val intent: CustomTabsIntent = CustomTabsIntent.Builder().build()
    intent.launchUrl(context, Uri.parse(newsItem.link))
}