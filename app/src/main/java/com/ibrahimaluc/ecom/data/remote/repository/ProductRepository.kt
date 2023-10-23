package com.ibrahimaluc.ecom.data.remote.repository

import com.ibrahimaluc.ecom.core.util.Resource
import com.ibrahimaluc.ecom.data.local.favorite.FavoriteEntity
import com.ibrahimaluc.ecom.data.local.favorite.FavoriteProductsDAO
import com.ibrahimaluc.ecom.data.remote.ProductService
import com.ibrahimaluc.ecom.data.remote.model.productDetail.ProductDetail
import com.ibrahimaluc.ecom.data.remote.model.productHome.ProductHome
import com.ibrahimaluc.ecom.data.remote.model.productSearch.ProductSearch
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ProductRepository(
    private val productService: ProductService,
    private val favoriteProductsDAO: FavoriteProductsDAO
) {
    fun getAllProducts(): Flow<Resource<ProductHome>> = callbackFlow {
        try {
            trySend(Resource.Success(productService.getAllProducts()))
        } catch (e: Exception) {
            trySend(Resource.Error(e.message.orEmpty()))
        }

        awaitClose { channel.close() }
    }

    fun getSearchResults(search_query: String): Flow<Resource<ProductSearch>> = callbackFlow {
        try {
            trySend(Resource.Success(productService.getSearchResults(search_query)))
        } catch (e: Exception) {
            trySend(Resource.Error(e.message.orEmpty()))
        }

        awaitClose { channel.close() }
    }

    fun getProductDetail(product_id: String): Flow<Resource<ProductDetail>> = callbackFlow {
        try {
            trySend(Resource.Success(productService.getProductDetail(product_id)))
        } catch (e: Exception) {
            trySend(Resource.Error(e.message.orEmpty()))
        }

        awaitClose { channel.close() }
    }

    suspend fun checkProducts(): List<FavoriteEntity> {
        return favoriteProductsDAO.getFavoriteProducts()
    }

    suspend fun addFavoriteProductRoom(favProduct: FavoriteEntity) =
        favoriteProductsDAO.addFavorite(favProduct)

    suspend fun deleteFavoriteProductRoom(favProduct: FavoriteEntity) =
        favoriteProductsDAO.deleteFavorite(favProduct)
}