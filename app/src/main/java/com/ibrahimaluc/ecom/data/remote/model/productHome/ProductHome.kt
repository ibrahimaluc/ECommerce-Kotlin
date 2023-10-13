package com.ibrahimaluc.ecom.data.remote.model.productHome


import com.google.gson.annotations.SerializedName

data class ProductHome(
    @SerializedName("products")
    val products: List<Product>
)