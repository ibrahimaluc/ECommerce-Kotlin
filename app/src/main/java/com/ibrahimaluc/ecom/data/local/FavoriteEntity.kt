package com.ibrahimaluc.ecom.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String?,
    val price: Double?,
    val images: String?,
    var isFavorited: Boolean = false,

)
