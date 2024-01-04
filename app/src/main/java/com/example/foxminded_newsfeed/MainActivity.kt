package com.example.foxminded_newsfeed

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.foxminded_newsfeed.ui.theme.PrimaryOrange
import com.example.foxminded_newsfeed.ui.theme.White

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
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
            selectedIcon = ImageVector.vectorResource(R.drawable.eye_selected),
            unselectedIcon = ImageVector.vectorResource(R.drawable.eye)
        ), NavItemState(
            selectedIcon = ImageVector.vectorResource(R.drawable.layers_selected),
            unselectedIcon = ImageVector.vectorResource(R.drawable.layers)
        ), NavItemState(
            selectedIcon = ImageVector.vectorResource(R.drawable.layers_selected),
            unselectedIcon = ImageVector.vectorResource(R.drawable.layers)
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
            NavigationBar {
                items.forEachIndexed { index, navItemState ->
                    NavigationBarItem(selected = bottomNavState == index,
                        onClick = { bottomNavState = index },
                        icon = {
                            if (bottomNavState == index) navItemState.selectedIcon
                            else navItemState.unselectedIcon
                        })
                }
            }
        }

    ) {

    }


}

data class NavItemState(
    val selectedIcon: ImageVector, val unselectedIcon: ImageVector
)
