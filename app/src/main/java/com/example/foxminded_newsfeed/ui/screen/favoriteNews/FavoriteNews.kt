package com.example.foxminded_newsfeed.ui.screen.favoriteNews

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.foxminded_newsfeed.data.room.NewsEntity
import com.example.foxminded_newsfeed.ui.LazyItemsColumn
import com.example.foxminded_newsfeed.ui.MainViewModel

@Composable
fun FavoriteNews(mainViewModel: MainViewModel) {
    val uiState by mainViewModel.uiState.collectAsState()
    val context = LocalContext.current

    LazyItemsColumn(listNewsItems = uiState.favoriteNews,
        onFavoriteButtonClick = { mainViewModel.clickFavoriteButton(it) },
        onItemCLick = { openChromeCustomTabs(newsEntity = it, context = context) })
}

fun openChromeCustomTabs(newsEntity: NewsEntity, context: Context) {
    val intent: CustomTabsIntent = CustomTabsIntent.Builder().build()
    intent.launchUrl(context, Uri.parse(newsEntity.link))
}