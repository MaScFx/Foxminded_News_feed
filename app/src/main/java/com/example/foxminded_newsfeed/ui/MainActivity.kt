package com.example.foxminded_newsfeed.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foxminded_newsfeed.R
import com.example.foxminded_newsfeed.ui.screen.allNews.AllNews
import com.example.foxminded_newsfeed.ui.screen.allNews.AllNewsVM
import com.example.foxminded_newsfeed.ui.screen.favoriteNews.FavoriteNews
import com.example.foxminded_newsfeed.ui.screen.favoriteNews.FavoriteNewsVM
import com.example.foxminded_newsfeed.ui.screen.newsFromSelectedProvider.NewsFromSelectedProvider
import com.example.foxminded_newsfeed.ui.screen.newsFromSelectedProvider.NewsFromSelectedProviderVM
import com.example.foxminded_newsfeed.ui.theme.DarkGrey
import com.example.foxminded_newsfeed.ui.theme.LightGrey
import com.example.foxminded_newsfeed.ui.theme.PrimaryOrange
import com.example.foxminded_newsfeed.ui.theme.White
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val allNewsVM: AllNewsVM by viewModels()
        val favoriteNewsVM: FavoriteNewsVM by viewModels()
        val newsFromSelectedVM: NewsFromSelectedProviderVM by viewModels()
        val mainActivityVM: MainActivityVM by viewModels()

        setContent {
            MainScreen(
                allNewsVM = allNewsVM,
                favoriteNewsVM = favoriteNewsVM,
                newsFromSelectedProviderVM = newsFromSelectedVM,
                mainActivityVM = mainActivityVM
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class,
    ExperimentalFoundationApi::class
)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    allNewsVM: AllNewsVM,
    favoriteNewsVM: FavoriteNewsVM,
    newsFromSelectedProviderVM: NewsFromSelectedProviderVM,
    mainActivityVM: MainActivityVM
) {
    val uiState = mainActivityVM.generalUIState.collectAsState()
    val items = listOf<NavItemState>(
        NavItemState(
            contentDescription = stringResource(R.string.all_news),
            icon = ImageVector.vectorResource(R.drawable.eye),
        ), NavItemState(
            contentDescription = stringResource(R.string.select_news_provider),
            icon = ImageVector.vectorResource(R.drawable.layers),
        ), NavItemState(
            contentDescription = stringResource(R.string.selected_articles),
            icon = ImageVector.vectorResource(R.drawable.bookmark_selected),
        )
    )
    var bottomNavState by rememberSaveable {
        mutableIntStateOf(0)
    }
//    val pagerState = rememberPagerState(pageCount = { items.size })
    val pagerState = rememberPagerState(pageCount = { items.size })

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = stringResource(R.string.news_feed),
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.poppins)),
                modifier = Modifier.fillMaxWidth()
            )
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = PrimaryOrange, titleContentColor = White
        ), actions = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.bookmark),
                    contentDescription = stringResource(R.string.bookmark)
                )
            }
        })
    },

        bottomBar = {
            NavigationBar(containerColor = DarkGrey) {
                items.forEachIndexed { index, navItemState ->
                    NavigationBarItem(
                        selected = bottomNavState == index,
                        onClick = {
                            bottomNavState = index
//                            coroutineScope.launch {
//                                pagerState.animateScrollToPage(index)
//                            }
//                            BottomNavItemClickHandler(index, pagerState)
//                            coroutineScope {
//
//                            }
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }


//                            pagerState.animateScrollToPage(index)
                        },
                        icon = {
                            Icon(
                                imageVector = navItemState.icon,
                                tint = if (bottomNavState == index) PrimaryOrange
                                else LightGrey,
                                contentDescription = navItemState.contentDescription,
                            )
                        }

                    )
                }
            }
        }

    ) { padding ->

        val pullRefreshState = rememberPullRefreshState(
            refreshing = uiState.value.isRefreshing,
            onRefresh = { mainActivityVM.refreshNews() }
        )
        HorizontalPager(state = pagerState) { page ->
            bottomNavState = pagerState.currentPage
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .pullRefresh(pullRefreshState)
            ) {

                when (page) {
                    0 -> AllNews(allNewsVM = allNewsVM)
                    1 -> NewsFromSelectedProvider(newsFromSelectedProviderVM = newsFromSelectedProviderVM)
                    2 -> FavoriteNews(favoriteNewsVM = favoriteNewsVM)
                }

                PullRefreshIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(50.dp),
                    refreshing = uiState.value.isRefreshing,
                    state = pullRefreshState,
                    contentColor = PrimaryOrange
//                contentColor = Brush.horizontalGradient(listOf(PrimaryOrange, White))
                )
            }
//            CircularProgressIndicator(
//                modifier = Modifier.align(Alignment.Center).size(70.dp),
//                strokeWidth = 17.dp,
//                color = Brush.horizontalGradient(listOf(PrimaryOrange, White),  tileMode = TileMode.Repeated),
//                strokeCap = StrokeCap.Round
//
//            )

        }
    }
}

data class NavItemState(
    val icon: ImageVector, val contentDescription: String
)

