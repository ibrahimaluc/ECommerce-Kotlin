package com.ibrahimaluc.ecom.ui.screen.search

import com.ibrahimaluc.ecom.domain.model.productSearch.SearchResult
import java.util.ArrayList

data class SearchUiState(
    var searchList : ArrayList<SearchResult>? = arrayListOf(),
    var isLoading:Boolean?=true
)
