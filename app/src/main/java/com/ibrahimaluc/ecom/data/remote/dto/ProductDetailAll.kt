package com.ibrahimaluc.ecom.data.remote.dto


import com.google.gson.annotations.SerializedName

data class ProductDetailAll(
    @SerializedName("brand")
    val brand: String?,
    @SerializedName("categories")
    val categories: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("features")
    val features: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("images")
    val images: List<String>?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("price")
    val price: Double?,
    @SerializedName("seller")
    val seller: String?
)
