package com.ibrahimaluc.ecom.domain.model.productSearch


import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
)