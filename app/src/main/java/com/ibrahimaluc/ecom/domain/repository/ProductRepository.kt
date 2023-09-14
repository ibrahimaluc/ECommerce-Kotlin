package com.ibrahimaluc.ecom.domain.repository

import com.ibrahimaluc.ecom.core.util.Resource
import com.ibrahimaluc.ecom.domain.model.productHome.ProductHome
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun  getAllProduct(): Flow<Resource<ProductHome>>
}