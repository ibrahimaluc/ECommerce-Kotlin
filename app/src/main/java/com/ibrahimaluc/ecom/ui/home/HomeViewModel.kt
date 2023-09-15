package com.ibrahimaluc.ecom.ui.home

import com.ibrahimaluc.ecom.core.base.BaseViewModel
import com.ibrahimaluc.ecom.domain.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(
    private val productRepository: ProductRepository
) : BaseViewModel() {
    private val _state: MutableStateFlow<HomeUiState> =
        MutableStateFlow(HomeUiState(isLoading = false))
    val state: StateFlow<HomeUiState> get() = _state



}