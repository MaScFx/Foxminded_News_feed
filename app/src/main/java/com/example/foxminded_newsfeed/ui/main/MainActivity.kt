package com.example.foxminded_newsfeed.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.foxminded_newsfeed.ui.screen.allNews.AllNewsVM
import com.example.foxminded_newsfeed.ui.screen.favoriteNews.FavoriteNewsVM
import com.example.foxminded_newsfeed.ui.screen.selectedNews.SelectedNewsVM
import com.example.foxminded_newsfeed.utils.NetworkConnectionStatus
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var networkConnectionStatus: NetworkConnectionStatus
    private val allNewsVM: AllNewsVM by viewModels()
    private val favoriteNewsVM: FavoriteNewsVM by viewModels()
    private val newsFromSelectedVM: SelectedNewsVM by viewModels()
    private val mainActivityVM: MainActivityVM by viewModels()
    private var isOnline = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen(
                allNewsVM = allNewsVM,
                favoriteNewsVM = favoriteNewsVM,
                selectedNewsVM = newsFromSelectedVM,
                mainActivityVM = mainActivityVM
            )
        }

        networkConnectionStatus.observe(this) {
            if (it && !isOnline) Toast.makeText(
                this@MainActivity, "Device is online! Swipe to refresh news", Toast.LENGTH_LONG
            ).show()

            if (!it) isOnline = false
        }
    }
}