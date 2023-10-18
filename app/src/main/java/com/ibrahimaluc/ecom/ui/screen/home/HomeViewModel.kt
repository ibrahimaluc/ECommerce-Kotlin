package com.ibrahimaluc.ecom.ui.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ibrahimaluc.ecom.core.base.BaseViewModel
import com.ibrahimaluc.ecom.core.util.Resource
import com.ibrahimaluc.ecom.data.local.favorite.FavoriteEntity
import com.ibrahimaluc.ecom.data.remote.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository,
) : BaseViewModel() {
    private val _state: MutableStateFlow<HomeUiState> =
        MutableStateFlow(HomeUiState(isLoading = false))

    private val _favoriteProducts = MutableLiveData<List<FavoriteEntity>>()
    val favoriteProducts: LiveData<List<FavoriteEntity>> get() = _favoriteProducts
    val state: StateFlow<HomeUiState> get() = _state

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
                            isLoading = false,
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

    suspend fun fetchFavoriteProducts(): List<FavoriteEntity> {
        return productRepository.checkProducts()
    }

    fun addFavoriteProductRoom(favoriteProduct: FavoriteEntity) = viewModelScope.launch {
        productRepository.addFavoriteProductRoom(favoriteProduct)

    }

    fun deleteFavWallpaperRoom(favoriteEntity: FavoriteEntity) = viewModelScope.launch {
        productRepository.deleteFavoriteProductRoom(favoriteEntity)

    }

}



