package com.example.foxminded_newsfeed.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [NewsEntity::class], version = 1, exportSchema = false)
@TypeConverters(ZonedDateTimeConverter::class)
abstract class MainDB : RoomDatabase() {
    abstract val dao: Dao

    companion object {
        fun getDataBase(context: Context): MainDB {
            var INSTANCE: MainDB? = null

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDB::class.java,
                    "main.db"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}