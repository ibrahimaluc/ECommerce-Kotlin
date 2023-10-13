package com.ibrahimaluc.ecom.ui.screen.home

import androidx.lifecycle.SavedStateHandle
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
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    private val _state: MutableStateFlow<HomeUiState> =
        MutableStateFlow(HomeUiState(isLoading = false))
    val state: StateFlow<HomeUiState> get() = _state

    private lateinit var favoriteProduct: FavoriteEntity
    private var fav = false


    init {
        getAllProducts()
        savedStateHandle.get<FavoriteEntity>("favoriteProduct")?.let {
            this@HomeViewModel.favoriteProduct = it
            checkFavoriteProduct(it)
        }
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

    private fun checkFavoriteProduct(favoriteProduct: FavoriteEntity) = viewModelScope.launch {
        productRepository.checkFavoriteProduct(favoriteProduct.id).collect {
            val currentState = _state.value
            _state.value = currentState.copy(isFavProduct = it)
        }
    }

    fun addFavoriteProductRoom(favoriteEntity: FavoriteEntity) = viewModelScope.launch {
        productRepository.addFavoriteProductRoom(favoriteEntity)
        fav = !fav
    }

    fun deleteFavWallpaperRoom() = viewModelScope.launch {
        productRepository.deleteFavoriteProductRoom(favoriteProduct)
    }
}



