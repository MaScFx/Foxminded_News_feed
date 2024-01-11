package com.example.foxminded_newsfeed

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.foxminded_newsfeed.ui.screen.AllNews
import com.example.foxminded_newsfeed.ui.screen.FavoriteNews
import com.example.foxminded_newsfeed.ui.screen.NewsFromSelectedProvider
import com.example.foxminded_newsfeed.ui.theme.DarkGrey
import com.example.foxminded_newsfeed.ui.theme.LightGrey
import com.example.foxminded_newsfeed.ui.theme.PrimaryOrange
import com.example.foxminded_newsfeed.ui.theme.White
import com.example.foxminded_newsfeed.vm.AllNewsVM
import com.example.foxminded_newsfeed.vm.FavoriteNewsVM
import com.example.foxminded_newsfeed.vm.NewsFromSelectedProviderVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val allNewsVM: AllNewsVM by viewModels()
        val favoriteNewsVM: FavoriteNewsVM by viewModels()
        val newsFromSelectedVM: NewsFromSelectedProviderVM by viewModels()

        setContent {
            Greeting(
                allNewsVM = allNewsVM,
                favoriteNewsVM = favoriteNewsVM,
                newsFromSelectedProviderVM = newsFromSelectedVM
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Greeting(
    modifier: Modifier = Modifier,
    allNewsVM: AllNewsVM,
    favoriteNewsVM: FavoriteNewsVM,
    newsFromSelectedProviderVM: NewsFromSelectedProviderVM
) {

    val items = listOf<NavItemState>(
        NavItemState(
            contentDescription = "all news",
            selectedIcon = ImageVector.vectorResource(R.drawable.eye_selected),
            unselectedIcon = ImageVector.vectorResource(R.drawable.eye)
        ), NavItemState(
            contentDescription = "select news provider",
            selectedIcon = ImageVector.vectorResource(R.drawable.layers_selected),
            unselectedIcon = ImageVector.vectorResource(R.drawable.layers)
        ), NavItemState(
            contentDescription = "selected articles",
            selectedIcon = ImageVector.vectorResource(R.drawable.bookmark_selected),
            unselectedIcon = ImageVector.vectorResource(R.drawable.bookmark)
        )
    )

    var bottomNavState by rememberSaveable {
        mutableIntStateOf(0)
    }
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = "News Feed",
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
                    contentDescription = "bookmark"
                )
            }
        })
    },

        bottomBar = {
            NavigationBar(containerColor = DarkGrey) {
                items.forEachIndexed { index, navItemState ->
                    NavigationBarItem(selected = bottomNavState == index,
                        onClick = { bottomNavState = index },
                        icon = {
                            Icon(
                                imageVector = if (bottomNavState == index) navItemState.unselectedIcon
                                else navItemState.selectedIcon,
                                tint = if (bottomNavState == index) PrimaryOrange
                                else LightGrey,
                                contentDescription = navItemState.contentDescription,
                            )
                        }

                    )
                }
            }
        }

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (bottomNavState) {
                0 -> AllNews(allNewsVM = allNewsVM)
                1 -> NewsFromSelectedProvider(newsFromSelectedProviderVM = newsFromSelectedProviderVM)
                2 -> FavoriteNews(favoriteNewsVM = favoriteNewsVM)
            }
        }
    }
}

data class NavItemState(
    val selectedIcon: ImageVector, val unselectedIcon: ImageVector, val contentDescription: String
)


