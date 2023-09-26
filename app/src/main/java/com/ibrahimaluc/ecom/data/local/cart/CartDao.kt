package com.ibrahimaluc.ecom.data.local.cart

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CartDao {
    @Insert
    suspend fun insert(cart: CartEntity)

    @Query("SELECT * FROM cart")
    suspend fun getCartProducts(): List<CartEntity>

    @Query("SELECT * FROM cart WHERE id=:id")
    suspend fun getCartEntityById(id: Int?): CartEntity?

    @Delete
    suspend fun delete(cart: CartEntity)

    @Query("SELECT * FROM cart WHERE id = :id AND size = :size")
    suspend fun getCartEntityByIdAndSize(id: Int, size: String): CartEntity?



}
