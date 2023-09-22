package com.ibrahimaluc.ecom.data.local.cart

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CartEntity::class], version = 1)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var instance: CartDatabase? = null

        fun getInstance(context: Context): CartDatabase {
            if (instance == null) {
                synchronized(CartDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CartDatabase::class.java,
                        "cart_database"
                    ).build()
                }
            }
            return instance!!
        }
    }


}