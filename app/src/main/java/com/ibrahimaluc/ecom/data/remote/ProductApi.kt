package com.ibrahimaluc.ecom.data.remote

import com.ibrahimaluc.ecom.data.util.ProductConstant
import com.ibrahimaluc.ecom.domain.model.productHome.ProductHome
import retrofit2.http.GET
import retrofit2.http.Headers

interface ProductApi {

    @GET("api/product")
    @Headers("Authorization: ${ProductConstant.TOKEN}")
    suspend fun getAllProducts(
    ): ProductHome
}