package com.example.foxminded_newsfeed.ui.screen.selectedNews

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.foxminded_newsfeed.R
import com.example.foxminded_newsfeed.domain.model.NewsItem
import com.example.foxminded_newsfeed.domain.model.NewsSource
import com.example.foxminded_newsfeed.ui.LazyItemsColumn
import com.example.foxminded_newsfeed.ui.theme.SourceColorReddit
import com.example.foxminded_newsfeed.ui.theme.SourceColorWelt
import com.example.foxminded_newsfeed.utils.getFakeNewsItems

@Composable
fun NewsFromSelectedProvider(selectedNewsVM: SelectedNewsVM) {
    val uiState by selectedNewsVM.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Column {
        Text(
            text = stringResource(R.string.all_groups),
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.poppins)),
            modifier = Modifier.padding(start = 26.dp, top = 16.dp, bottom = 22.dp)
        )
        Row {
            Button(
                onClick = { selectedNewsVM.newsFromSelectedSource(NewsSource.WELT) },
                colors = ButtonDefaults.buttonColors(containerColor = SourceColorWelt),
                modifier = Modifier
                    .weight(0.4f)
                    .padding(start = 16.dp, end = 8.dp)
            ) {
                Text(
                    text = NewsSource.WELT.name,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                )
            }
            Button(
                onClick = { selectedNewsVM.newsFromSelectedSource(NewsSource.Reddit) },
                colors = ButtonDefaults.buttonColors(containerColor = SourceColorReddit),
                modifier = Modifier
                    .weight(0.6f)
                    .padding(end = 16.dp)
            ) {
                Text(
                    text = NewsSource.Reddit.name,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.poppins))
                )
            }
        }
        LazyItemsColumn(
            listNewsItems = uiState.selectedNews,
            onFavoriteButtonClick = { selectedNewsVM.clickFavoriteButton(it) },
            onItemCLick = {openChromeCustomTabs(newsItem = it, context = context)})
    }
}

fun openChromeCustomTabs(newsItem: NewsItem, context: Context) {
    val intent: CustomTabsIntent = CustomTabsIntent.Builder()
        .build()
    intent.launchUrl(context, Uri.parse(newsItem.link))

}

@Preview
@Composable
fun NewsFromSelectedProviderPreview() {
    val listFakeItems = getFakeNewsItems()
    LazyItemsColumn(listFakeItems, {}, {})
}
