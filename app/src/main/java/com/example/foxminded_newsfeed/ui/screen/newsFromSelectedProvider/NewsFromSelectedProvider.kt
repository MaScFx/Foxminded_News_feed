package com.example.foxminded_newsfeed.ui.screen.newsFromSelectedProvider

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.foxminded_newsfeed.utils.getFakeNewsItems
import com.example.foxminded_newsfeed.ui.LazyItemsColumn

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsFromSelectedProvider (newsFromSelectedProviderVM: NewsFromSelectedProviderVM){
    val newsList by newsFromSelectedProviderVM.uiState.collectAsStateWithLifecycle()

    Column {
        Row {

        }
        LazyItemsColumn(newsList.newsList)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun NewsFromSelectedProviderPreview(){
    val listFakeItems = getFakeNewsItems()
    LazyItemsColumn(listNewsItems = listFakeItems)
}
