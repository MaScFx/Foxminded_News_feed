package com.example.foxminded_newsfeed.ui.screen.allNews

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.foxminded_newsfeed.ui.LazyItemsColumn

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AllNews (allNewsVM: AllNewsVM){
    val newsList by allNewsVM.uiState.collectAsStateWithLifecycle()
    LazyItemsColumn(newsList.newsList)
}