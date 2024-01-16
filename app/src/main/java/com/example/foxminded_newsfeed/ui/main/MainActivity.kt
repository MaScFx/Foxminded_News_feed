package com.example.foxminded_newsfeed.ui.main

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.example.foxminded_newsfeed.ui.screen.allNews.AllNewsVM
import com.example.foxminded_newsfeed.ui.screen.favoriteNews.FavoriteNewsVM
import com.example.foxminded_newsfeed.ui.screen.newsFromSelectedProvider.NewsFromSelectedProviderVM
import com.example.foxminded_newsfeed.utils.checkOnlineState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val allNewsVM: AllNewsVM by viewModels()
        val favoriteNewsVM: FavoriteNewsVM by viewModels()
        val newsFromSelectedVM: NewsFromSelectedProviderVM by viewModels()
        val mainActivityVM: MainActivityVM by viewModels()
        var isOnline = true

        setContent {
            MainScreen(
                allNewsVM = allNewsVM,
                favoriteNewsVM = favoriteNewsVM,
                newsFromSelectedProviderVM = newsFromSelectedVM,
                mainActivityVM = mainActivityVM
            )
        }

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                while (true) {
                    val oldStatus = isOnline
                    isOnline = checkOnlineState(this@MainActivity)

                    if (!oldStatus && isOnline) {
                        runOnUiThread {
                            Toast.makeText(
                                this@MainActivity,
                                "Device is online! Swipe to refresh news",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                    delay(1000)

                }
            }
        }
    }
}