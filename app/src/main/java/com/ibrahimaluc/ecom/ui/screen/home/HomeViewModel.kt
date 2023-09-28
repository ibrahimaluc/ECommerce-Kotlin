package com.ibrahimaluc.ecom.ui.screen.home

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
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : BaseViewModel() {
    private val _state: MutableStateFlow<HomeUiState> =
        MutableStateFlow(HomeUiState(isLoading = false))
    val state: StateFlow<HomeUiState> get() = _state

    private val _searchState: MutableStateFlow<HomeUiState> =
        MutableStateFlow(HomeUiState(isLoading = false))
    val searchState: StateFlow<HomeUiState> get() = _searchState

    init {
        getAllProducts()
    }

    private fun getAllProducts() {
        job = viewModelScope.launch {
            productRepository.getAllProducts().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = HomeUiState(
                            productList = result.data?.products,
                            isLoading = false
                        )
                    }

                    is Resource.Error -> {
                        _state.value = HomeUiState(
                            isLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = HomeUiState(
                            isLoading = true
                        )
                    }
                }
            }.launchIn(this)
        }
    }


}



