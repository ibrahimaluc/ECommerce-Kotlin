package com.ibrahimaluc.ecom.ui.screen.detail

import com.ibrahimaluc.ecom.data.remote.model.productDetail.ProductDetailAll

data class DetailUiState(
    val isLoading: Boolean,
    val productDetail: ProductDetailAll? = null
)
