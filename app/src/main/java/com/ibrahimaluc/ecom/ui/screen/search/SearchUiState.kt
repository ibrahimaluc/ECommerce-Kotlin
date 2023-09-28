package com.ibrahimaluc.ecom.ui.screen.search

import com.ibrahimaluc.ecom.domain.model.productSearch.SearchResult

data class SearchUiState(
    val searchList: List<SearchResult>? = arrayListOf(),
    val isLoading: Boolean
)
