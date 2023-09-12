package com.ibrahimaluc.ecom.domain.model.productHome


import com.google.gson.annotations.SerializedName

data class ProductHome(
    @SerializedName("products")
    val products: List<Product>?
)