package com.ibrahimaluc.ecom.domain.model.productSearch


import com.google.gson.annotations.SerializedName

data class ProductSearch(
    @SerializedName("search_result")
    val searchResult: List<SearchResult>?
)