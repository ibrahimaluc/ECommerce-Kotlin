package com.ibrahimaluc.ecom.ui.screen.favorite

import androidx.lifecycle.viewModelScope
import com.ibrahimaluc.ecom.core.base.BaseViewModel
import com.ibrahimaluc.ecom.data.local.favorite.FavoriteEntity
import com.ibrahimaluc.ecom.data.remote.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : BaseViewModel() {


    suspend fun checkProducts(): List<FavoriteEntity> {
        return productRepository.checkProducts()
    }

    suspend fun deleteFavWallpaperRoom(favoriteEntity: FavoriteEntity) = viewModelScope.launch {
        productRepository.deleteFavoriteProductRoom(favoriteEntity)

    }
}