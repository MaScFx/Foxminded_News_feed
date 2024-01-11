package com.example.foxminded_newsfeed.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert
    suspend fun insertLastNewsEntity(lastNewsEntity: LastNewsEntity)

    @Insert
    suspend fun insertFavoriteNewsEntity(favoriteNewsEntity: FavoriteNewsEntity)

    @Delete
    suspend fun deleteLastNewsEntity(lastNewsEntity: LastNewsEntity)

    @Delete
    suspend fun deleteFavoriteNewsEntity(lastNewsEntity: FavoriteNewsEntity)

    @Update
    suspend fun updateLastNewsEntity(lastNewsEntity: LastNewsEntity)

    @Update
    suspend fun updateFavoriteNewsEntity(lastNewsEntity: FavoriteNewsEntity)

    @Query("SELECT * FROM LastNewsEntity")
    fun getAllLastNewsEntity(): Flow<List<LastNewsEntity>>
    @Query("SELECT * FROM FavoriteNewsEntity")
    fun getAllFavoriteNewsEntity(): Flow<List<FavoriteNewsEntity>>

}