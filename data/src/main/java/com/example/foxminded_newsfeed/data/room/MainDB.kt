package com.example.foxminded_newsfeed.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [LastNewsEntity::class, FavoriteNewsEntity::class], version = 1)
@TypeConverters(ZonedDateTimeConverter::class)
abstract class MainDB : RoomDatabase() {
    abstract val dao: Dao

    companion object {
        fun createDataBase(context: Context): MainDB {
            return Room.databaseBuilder(context, MainDB::class.java, "main.db").build()
        }
    }
}