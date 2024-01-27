package com.example.foxminded_newsfeed.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.foxminded_newsfeed.R
import com.example.foxminded_newsfeed.ui.MainViewModel
import com.example.foxminded_newsfeed.utils.NetworkConnectionStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var networkConnectionStatus: NetworkConnectionStatus
    private val mainViewModel: MainViewModel by viewModels()
    private var isOnline = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.refreshNews()
        lifecycleScope.launch {
            mainViewModel.favoriteNewsShow()
        }
        setContent {
            MainScreen(
                mainViewModel = mainViewModel
            )
        }

        networkConnectionStatus.observe(this) {
            if (it && !isOnline) Toast.makeText(
                this@MainActivity,
                getString(R.string.device_is_online_swipe_to_refresh_news), Toast.LENGTH_LONG
            ).show()

            if (!it) isOnline = false
        }
    }
}