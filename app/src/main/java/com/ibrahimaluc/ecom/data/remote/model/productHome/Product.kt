package com.ibrahimaluc.ecom.data.remote.model.productHome


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("images")
    val images: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("price")
    val price: Double?,
    @SerializedName("point")
    val point :Float?,
)