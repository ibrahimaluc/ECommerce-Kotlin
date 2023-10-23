package com.ibrahimaluc.ecom.data.local.favorite

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteEntity::class], version = 1, exportSchema = false)
abstract class FavoriteProductsRoomDB : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteProductsDAO


}