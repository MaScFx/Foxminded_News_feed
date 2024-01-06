package com.example.foxminded_newsfeed.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.foxminded_newsfeed.domain.model.NewsItem
import com.example.foxminded_newsfeed.ui.compose.LazyItemsColumn
import com.example.foxminded_newsfeed.vm.NewsFromSelectedProviderVM

@Composable
fun NewsFromSelectedProvider (newsFromSelectedProviderVM: NewsFromSelectedProviderVM){

    val newsList by newsFromSelectedProviderVM.uiState.collectAsStateWithLifecycle()
    LazyItemsColumn(newsList.newsList)
}