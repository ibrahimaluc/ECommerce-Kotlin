package com.ibrahimaluc.ecom.domain.repository

import com.ibrahimaluc.ecom.core.util.Resource
import com.ibrahimaluc.ecom.domain.model.productHome.ProductHome
import com.ibrahimaluc.ecom.domain.model.productSearch.ProductSearch
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getAllProducts(): Flow<Resource<ProductHome>>

    suspend fun getSearchResults(): Flow<Resource<ProductSearch>>
}