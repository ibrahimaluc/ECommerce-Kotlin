package com.ibrahimaluc.ecom.ui.home

import com.ibrahimaluc.ecom.data.remote.dto.ProductHome

data class HomeUiState(
    val productList: ArrayList<ProductHome>? = arrayListOf(),
    val isLoading: Boolean? = true

)