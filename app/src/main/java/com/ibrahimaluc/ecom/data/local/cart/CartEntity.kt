package com.ibrahimaluc.ecom.data.local.cart

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String?,
    val price: Double?,
    val images: String?,
    val seller: String?,
    val size: String?,
    var quantity: Int = 1,
)
