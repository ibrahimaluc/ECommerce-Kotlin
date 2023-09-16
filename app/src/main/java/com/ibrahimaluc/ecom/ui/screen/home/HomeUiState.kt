package com.ibrahimaluc.ecom.ui.screen.home

import com.ibrahimaluc.ecom.domain.model.productHome.Product
import java.util.ArrayList


data class HomeUiState(
    val productList: ArrayList<Product>? = arrayListOf(),
    val isLoading: Boolean

)