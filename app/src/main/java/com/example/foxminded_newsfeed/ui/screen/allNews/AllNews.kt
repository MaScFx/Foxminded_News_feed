package com.example.foxminded_newsfeed.ui.screen.allNews

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.foxminded_newsfeed.data.room.NewsEntity
import com.example.foxminded_newsfeed.ui.LazyItemsColumn
import com.example.foxminded_newsfeed.ui.MainViewModel

@Composable
fun AllNews(mainViewModel: MainViewModel) {
    val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current


    Box(modifier = Modifier.fillMaxSize()) {
        LazyItemsColumn(listNewsItems = uiState.allNews,
            onFavoriteButtonClick = { mainViewModel.clickFavoriteButton(it) },
            onItemCLick = { openChromeCustomTabs(newsEntity = it, context = context) })
    }
}

fun openChromeCustomTabs(newsEntity: NewsEntity, context: Context) {
    val intent: CustomTabsIntent = CustomTabsIntent.Builder().build()
    intent.launchUrl(context, Uri.parse(newsEntity.link))
}