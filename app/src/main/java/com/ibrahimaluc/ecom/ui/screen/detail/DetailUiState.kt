package com.ibrahimaluc.ecom.ui.screen.detail

import com.ibrahimaluc.ecom.data.remote.model.productDetail.ProductDetail

data class DetailUiState(
    val isLoading: Boolean,
    val productDetail: ProductDetail? = null
)
