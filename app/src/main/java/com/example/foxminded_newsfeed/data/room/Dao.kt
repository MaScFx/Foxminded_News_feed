package com.example.foxminded_newsfeed.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(lastNewsEntity: NewsEntity)

    @Query("DELETE FROM NewsEntity WHERE id = :newsId")
    suspend fun delete(newsId: String)

    @Query("SELECT * FROM NewsEntity")
    fun getAllNews(): List<NewsEntity>

    @Query("SELECT * FROM NewsEntity WHERE isFavorite = 1")
    fun getFavoriteNews(): Flow<List<NewsEntity>>

    @Query("SELECT * FROM NewsEntity WHERE id = :newsId")
    fun getByID(newsId: String): NewsEntity?
}