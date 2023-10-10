package com.ibrahimaluc.ecom.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.ibrahimaluc.ecom.data.remote.model.productHome.Product

data class ProductHome(
    @SerializedName("products")
    val products: List<Product>?
)
