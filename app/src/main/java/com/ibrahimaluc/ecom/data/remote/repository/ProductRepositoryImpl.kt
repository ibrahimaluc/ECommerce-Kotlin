package com.ibrahimaluc.ecom.data.remote.repository

import com.ibrahimaluc.ecom.core.util.Resource
import com.ibrahimaluc.ecom.data.remote.ProductApi
import com.ibrahimaluc.ecom.domain.model.productHome.ProductHome
import com.ibrahimaluc.ecom.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class ProductRepositoryImpl(
    private val api: ProductApi
) : ProductRepository {
    override suspend fun getAllProduct(): Flow<Resource<ProductHome>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getAllProducts()
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Http Error!"))
        } catch (ioException: IOException) {
            emit(Resource.Error(message = "Network Error: ${ioException.localizedMessage}"))
        }

    }
}