package com.ibrahimaluc.ecom.data.local.favorite

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteProductsDAO {
    @Insert
    suspend fun addFavorite(favorite: FavoriteEntity)

    @Query("SELECT * FROM favorite")
    suspend fun getFavoriteProducts(): List<FavoriteEntity>

    @Query("SELECT * FROM favorite WHERE id=:id")
    suspend fun getFavoriteEntityById(id: Int?): FavoriteEntity?

    @Delete
    suspend fun deleteFavorite(favorite: FavoriteEntity)
}