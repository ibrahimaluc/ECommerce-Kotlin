package com.ibrahimaluc.ecom.data.remote.model.productDetail


import com.google.gson.annotations.SerializedName

data class ProductDetail(
    @SerializedName("product_detail")
    val productDetail: ProductDetailAll?
)