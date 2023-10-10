package com.ibrahimaluc.ecom.data.remote.repository

import com.ibrahimaluc.ecom.core.util.Resource
import com.ibrahimaluc.ecom.data.remote.ProductService
import com.ibrahimaluc.ecom.data.remote.model.productDetail.ProductDetail
import com.ibrahimaluc.ecom.data.remote.model.productHome.ProductHome
import com.ibrahimaluc.ecom.data.remote.model.productSearch.ProductSearch
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ProductRepository(
    private val productService: ProductService,
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

//    override suspend fun getAllProducts(): Flow<Resource<ProductHome>> = flow {
//        emit(Resource.Loading())
//        try {
//            val response = api.getAllProducts()
//            emit(Resource.Success(response))
//        } catch (e: HttpException) {
//            emit(Resource.Error(message = "Http Error!"))
//        } catch (ioException: IOException) {
//            emit(Resource.Error(message = "Network Error: ${ioException.localizedMessage}"))
//        }
//    }
//
//    override suspend fun getSearchResults(search_query: String): Flow<Resource<ProductSearch>> =
//        flow {
//            emit(Resource.Loading())
//            try {
//                val response = api.getSearchResults(search_query)
//                emit(Resource.Success(response))
//            } catch (e: HttpException) {
//                emit(Resource.Error(message = "Http Error!"))
//            } catch (ioException: IOException) {
//                emit(Resource.Error(message = "Network Error: ${ioException.localizedMessage}"))
//            }
//        }
//
//    override suspend fun getProductDetail(product_id: String): Flow<Resource<ProductDetail>> =
//        flow {
//            emit(Resource.Loading())
//            try {
//                val response = api.getProductDetail(product_id)
//                emit(Resource.Success(response))
//            } catch (e: HttpException) {
//                emit(Resource.Error(message = "Http Error!"))
//            } catch (ioException: IOException) {
//                emit(Resource.Error(message = "Network Error: ${ioException.localizedMessage}"))
//            }
//        }
}