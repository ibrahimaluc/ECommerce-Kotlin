package com.ibrahimaluc.ecom.ui.screen.home

import com.ibrahimaluc.ecom.domain.model.productHome.Product
import com.ibrahimaluc.ecom.domain.model.productSearch.SearchResult

data class HomeUiState(
    val productList:List<Product>? = arrayListOf(),
    val isLoading: Boolean

)