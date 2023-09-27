package com.ibrahimaluc.ecom.di

import com.ibrahimaluc.ecom.data.remote.ProductService
import com.ibrahimaluc.ecom.data.remote.repository.ProductRepositoryImpl
import com.ibrahimaluc.ecom.data.util.ProductConstant
import com.ibrahimaluc.ecom.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ProductModule {

    @Provides
    @Singleton
    fun provideProductRepositoryImpl(api: ProductService): ProductRepository {
        return ProductRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideProductApi(): ProductService {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        return Retrofit.Builder().baseUrl(ProductConstant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ProductService::class.java)
    }
}