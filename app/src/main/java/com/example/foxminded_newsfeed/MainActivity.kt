package com.example.foxminded_newsfeed

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.foxminded_newsfeed.model.NewsSource
import com.example.foxminded_newsfeed.model.Item
import com.example.foxminded_newsfeed.ui.compose.LazyItemsColumn
import com.example.foxminded_newsfeed.ui.theme.DarkGrey
import com.example.foxminded_newsfeed.ui.theme.LightGrey
import com.example.foxminded_newsfeed.ui.theme.PrimaryOrange
import com.example.foxminded_newsfeed.ui.theme.White

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            Greeting()
//            Foxminded_NewsFeedTheme {
            // A surface container using the 'background' color from the theme

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun Greeting(modifier: Modifier = Modifier) {
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
                0 -> LazyItemsColumn(listItems = getTestItemsNewsData())
                2 -> LazyItemsColumn(listItems = getTestItemsNewsData())
                1 -> Text(
                    text = items[bottomNavState].contentDescription, modifier = Modifier.align(
                        Alignment.Center
                    ), fontSize = 45.sp
                )
            }

        }

    }


}

data class NavItemState(
    val selectedIcon: ImageVector, val unselectedIcon: ImageVector, val contentDescription: String
)

fun getTestItemsNewsData(): List<Item> {
    val list = ArrayList<Item>()
    for (i in 0 until 30) {
        list.add(
            Item(
                isFavorites = false,
                time = "$i minutes ago",
                newsSource = NewsSource.BBC,
                title = "$i Good news!! Your DOG Win five million dollars! Graz!",
                imgUrl = "https://cpad.ask.fm/865/242/195/-9996995-2020i7r-er5rgsls2hkd5p0/large/_pcfqBsO9Ck.jpg",
            )
        )
    }
    return list
}
