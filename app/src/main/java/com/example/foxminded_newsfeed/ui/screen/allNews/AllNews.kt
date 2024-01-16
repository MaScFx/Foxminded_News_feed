package com.example.foxminded_newsfeed.ui.screen.allNews

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.foxminded_newsfeed.R
import com.example.foxminded_newsfeed.domain.model.NewsItem
import com.example.foxminded_newsfeed.ui.LazyItemsColumn
import com.example.foxminded_newsfeed.ui.theme.RedTransparent
import com.example.foxminded_newsfeed.ui.theme.White

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AllNews(allNewsVM: AllNewsVM) {
    val newsList by allNewsVM.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        LazyItemsColumn(listNewsItems = newsList.allNews,
            onFavoriteButtonClick = { allNewsVM.clickFavoriteButton(it) },
            onItemCLick = { openChromeCustomTabs(newsItem = it, context = context) })

        Box(modifier = Modifier.fillMaxSize()) {
            AnimatedVisibility(visible = newsList.showInternetConnectionError) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(RedTransparent)
                    .clickable { allNewsVM.hideErrorMessage() }) {

                    Column(modifier = Modifier.align(Alignment.Center)) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_info),
                            contentDescription = stringResource(R.string.no_internet_message),
                            modifier = Modifier
                                .width(50.dp)
                                .height(50.dp)
                                .align(Alignment.CenterHorizontally)
//                            alignment = Alignment.CenterHorizontally
                        )
                        Text(
                            text = stringResource(R.string.no_internet_message),
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_extrabold)),
                            color = White,
                            maxLines = 2

                        )

                    }
                }
            }
        }
    }
}

fun openChromeCustomTabs(newsItem: NewsItem, context: Context) {
    val intent: CustomTabsIntent = CustomTabsIntent.Builder().build()
    intent.launchUrl(context, Uri.parse(newsItem.link))

}