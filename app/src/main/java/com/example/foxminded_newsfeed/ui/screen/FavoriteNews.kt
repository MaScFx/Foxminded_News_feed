package com.example.foxminded_newsfeed.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.foxminded_newsfeed.domain.model.NewsItem
import com.example.foxminded_newsfeed.ui.compose.LazyItemsColumn
import com.example.foxminded_newsfeed.vm.FavoriteNewsVM

@Composable
fun FavoriteNews(favoriteNewsVM: FavoriteNewsVM){
    val newsList by favoriteNewsVM.uiState.collectAsStateWithLifecycle()
    LazyItemsColumn(newsList.newsList)
}