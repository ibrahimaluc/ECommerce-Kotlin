package com.ibrahimaluc.ecom.ui.screen.detail

import androidx.lifecycle.viewModelScope
import com.ibrahimaluc.ecom.core.base.BaseViewModel
import com.ibrahimaluc.ecom.core.util.Resource
import com.ibrahimaluc.ecom.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : BaseViewModel() {
    private val _state: MutableStateFlow<DetailUiState> =
        MutableStateFlow(DetailUiState(isLoading = false))
    val state: StateFlow<DetailUiState> get() = _state


    fun getAllDetail(product_id: String) {
        job = viewModelScope.launch {
            productRepository.getProductDetail(product_id).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = DetailUiState(
                            productDetail = result.data,
                            isLoading = false
                        )
                    }

                    is Resource.Error -> {
                        _state.value = DetailUiState(
                            isLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = DetailUiState(
                            isLoading = true
                        )
                    }
                }
            }.launchIn(this)
        }

    }
}