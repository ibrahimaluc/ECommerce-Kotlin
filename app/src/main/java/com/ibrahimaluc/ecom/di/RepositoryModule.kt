package com.ibrahimaluc.ecom.di

import com.ibrahimaluc.ecom.data.remote.ProductService
import com.ibrahimaluc.ecom.data.remote.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideProductRepository(
        productService: ProductService

    ): ProductRepository = ProductRepository(productService)
}