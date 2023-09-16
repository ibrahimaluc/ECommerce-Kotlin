package com.ibrahimaluc.ecom.data.remote

import com.ibrahimaluc.ecom.data.util.ProductConstant
import com.ibrahimaluc.ecom.domain.model.productHome.ProductHome
import com.ibrahimaluc.ecom.domain.model.productSearch.ProductSearch
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ProductApi {

    @GET("api/product")
    @Headers("Authorization: ${ProductConstant.TOKEN}")
    suspend fun getAllProducts(
    ): ProductHome

    @GET("api/product/search/")
    @Headers("Authorization: ${ProductConstant.TOKEN}")
    suspend fun getSearchResults(
        @Query("search_query") search_query: String
    ): ProductSearch
}