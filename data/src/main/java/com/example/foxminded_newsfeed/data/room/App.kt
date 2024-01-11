package com.example.foxminded_newsfeed.data.room



import android.app.Application

class App: Application() {
    val database by lazy { MainDB.createDataBase(this) }
}