package com.ibrahimaluc.ecom.data.remote.model.productDetail


import com.google.gson.annotations.SerializedName

data class ProductDetailAll(
    @SerializedName("brand")
    val brand: String,
    @SerializedName("categories")
    val categories: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("features")
    val features: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("images")
    var images: List<String>,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("seller")
    val seller: String,
    @SerializedName("point")
    val point :Float,
    @SerializedName("review")
    val review:Int
)