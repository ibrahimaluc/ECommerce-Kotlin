package com.ibrahimaluc.ecom.di

import android.content.Context
import androidx.room.Room
import com.ibrahimaluc.ecom.data.local.favorite.FavoriteProductsDAO
import com.ibrahimaluc.ecom.data.local.favorite.FavoriteProductsRoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDBModule {

    @Provides
    @Singleton
    fun provideFavoriteProductsRoomDB(@ApplicationContext appContext: Context): FavoriteProductsRoomDB =
        Room.databaseBuilder(
            appContext, FavoriteProductsRoomDB::class.java,
            "favoriteProducts_room.db"
        ).build()

    @Provides
    @Singleton
    fun provideFavoriteProductsDao(favoriteProductsRoomDB: FavoriteProductsRoomDB): FavoriteProductsDAO =
        favoriteProductsRoomDB.favoriteDao()
}