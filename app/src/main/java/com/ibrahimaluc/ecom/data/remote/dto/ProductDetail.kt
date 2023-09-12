package com.ibrahimaluc.ecom.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ProductDetail(
    @SerializedName("product_detail")
    val productDetail: ProductDetailAll?
)
