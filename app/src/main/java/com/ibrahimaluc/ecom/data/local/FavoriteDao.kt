package com.ibrahimaluc.ecom.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert
    suspend fun insert(favorite: FavoriteEntity)

    @Query("SELECT * FROM favorite")
    suspend fun getFavoriteProducts(): List<FavoriteEntity>

    @Query("SELECT * FROM favorite WHERE id=:id")
    suspend fun getFavoriteEntityById(id: Int?): FavoriteEntity?

    @Delete
    suspend fun delete(favorite: FavoriteEntity)
}