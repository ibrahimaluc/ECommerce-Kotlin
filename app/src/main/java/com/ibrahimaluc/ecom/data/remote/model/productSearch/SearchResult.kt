package com.ibrahimaluc.ecom.data.remote.model.productSearch


import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
)