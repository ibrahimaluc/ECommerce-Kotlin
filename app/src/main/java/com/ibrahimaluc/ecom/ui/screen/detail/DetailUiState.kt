package com.ibrahimaluc.ecom.ui.screen.detail

import com.ibrahimaluc.ecom.domain.model.productDetail.ProductDetail

data class DetailUiState(
    val isLoading: Boolean,
    val productDetail: ProductDetail? = null
)
