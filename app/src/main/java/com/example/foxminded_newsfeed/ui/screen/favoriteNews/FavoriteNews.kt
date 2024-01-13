package com.example.foxminded_newsfeed.ui.screen.favoriteNews

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.foxminded_newsfeed.ui.LazyItemsColumn

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FavoriteNews(favoriteNewsVM: FavoriteNewsVM) {
    val newsList by favoriteNewsVM.favoriteNewsFlow.collectAsState(initial = emptyList())
    LazyItemsColumn(
        listNewsItems = newsList,
        onFavoriteButtonClick = { favoriteNewsVM.clickFavoriteButton(it) })
}