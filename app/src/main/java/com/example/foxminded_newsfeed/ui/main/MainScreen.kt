package com.example.foxminded_newsfeed.ui.main

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foxminded_newsfeed.R
import com.example.foxminded_newsfeed.ui.MainViewModel
import com.example.foxminded_newsfeed.ui.screen.allNews.AllNews
import com.example.foxminded_newsfeed.ui.screen.favoriteNews.FavoriteNews
import com.example.foxminded_newsfeed.ui.screen.selectedNews.NewsFromSelectedProvider
import com.example.foxminded_newsfeed.ui.theme.DarkGrey
import com.example.foxminded_newsfeed.ui.theme.LightGrey
import com.example.foxminded_newsfeed.ui.theme.PrimaryOrange
import com.example.foxminded_newsfeed.ui.theme.RedTransparent
import com.example.foxminded_newsfeed.ui.theme.White
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterialApi::class,
    ExperimentalFoundationApi::class
)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel
) {
    val uiState = mainViewModel.uiState.collectAsState()
    val items = getNavItemState()
    var bottomNavState by rememberSaveable { mutableIntStateOf(0) }
    val pagerState = rememberPagerState(pageCount = { items.size })
    val scope = rememberCoroutineScope()


    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.news_feed),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    modifier = Modifier.fillMaxWidth()
                )
            }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = PrimaryOrange, titleContentColor = White
            )
        )
    },

        bottomBar = {
            NavigationBar(containerColor = DarkGrey) {
                items.forEachIndexed { index, navItemState ->
                    NavigationBarItem(selected = bottomNavState == index, onClick = {
                        bottomNavState = index
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }, icon = {
                        Icon(
                            imageVector = navItemState.icon,
                            tint = if (bottomNavState == index) PrimaryOrange
                            else LightGrey,
                            contentDescription = navItemState.contentDescription,
                        )
                    })
                }
            }
        }) { padding ->

        val pullRefreshState = rememberPullRefreshState(
            refreshing = uiState.value.isRefreshing,
            onRefresh = { mainViewModel.refreshNews() })

        HorizontalPager(state = pagerState) { page ->
            bottomNavState = pagerState.currentPage
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .pullRefresh(pullRefreshState)
            ) {

                when (page) {
                    0 -> AllNews(mainViewModel = mainViewModel)
                    1 -> NewsFromSelectedProvider(mainViewModel = mainViewModel)
                    2 -> FavoriteNews(mainViewModel = mainViewModel)
                }

                PullRefreshIndicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    refreshing = uiState.value.isRefreshing,
                    state = pullRefreshState
                )
                Box(modifier = Modifier.fillMaxSize()) {
                    AnimatedVisibility(visible = uiState.value.showInternetConnectionError) {
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .background(RedTransparent)
                            .clickable { mainViewModel.hideErrorMessage() }) {

                            Column(modifier = Modifier.align(Alignment.Center)) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_info),
                                    contentDescription = stringResource(R.string.no_internet_message),
                                    modifier = Modifier
                                        .width(50.dp)
                                        .height(50.dp)
                                        .align(Alignment.CenterHorizontally)
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
    }
}