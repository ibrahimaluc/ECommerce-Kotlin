package com.ibrahimaluc.ecom.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.ibrahimaluc.ecom.data.remote.model.productSearch.SearchResult

data class ProductSearch(
    @SerializedName("search_result")
    val searchResult: List<SearchResult>?
)
