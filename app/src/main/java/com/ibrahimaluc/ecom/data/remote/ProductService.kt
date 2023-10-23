package com.ibrahimaluc.ecom.data.remote

import com.ibrahimaluc.ecom.BuildConfig
import com.ibrahimaluc.ecom.data.remote.model.productDetail.ProductDetail
import com.ibrahimaluc.ecom.data.remote.model.productHome.ProductHome
import com.ibrahimaluc.ecom.data.remote.model.productSearch.ProductSearch
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {

    @GET("api/product")
    @Headers("Authorization: ${BuildConfig.TOKEN}")
    suspend fun getAllProducts(
    ): ProductHome

    @GET("api/product/search/")
    @Headers("Authorization: ${BuildConfig.TOKEN}")
    suspend fun getSearchResults(
        @Query("search_query") searchQuery: String
    ): ProductSearch

    @GET("api/product/{product_id}")
    @Headers("Authorization: ${BuildConfig.TOKEN}")
    suspend fun getProductDetail(
        @Path("product_id") productId: String
    ): ProductDetail
}