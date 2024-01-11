package com.example.foxminded_newsfeed.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.foxminded_newsfeed.domain.model.NewsItem
import com.example.foxminded_newsfeed.ui.compose.LazyItemsColumn
import com.example.foxminded_newsfeed.vm.AllNewsVM
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@Composable
//fun AllNews (listNewsItems: List<NewsItem>){
fun AllNews (allNewsVM: AllNewsVM){
    val newsList by allNewsVM.uiState.collectAsStateWithLifecycle()
    LazyItemsColumn(newsList.newsList)
}