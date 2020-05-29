package com.almarai.repository.di

import com.almarai.data_source.web.*
import com.almarai.repository.api.ProductsRepository
import com.almarai.repository.api.RoutesRepository
import com.almarai.repository.api_implementations.ProductsRepositoryImplementation
import com.almarai.repository.api_implementations.RoutesRepositoryImplementation
import org.koin.dsl.module

val RepositoryModule = module {
    single { WebService() }
    single<WebRoutesDataSource> { WebRoutesDataSourceImplementation(get()) }
    single<WebProductsDataSource> { WebProductsDataSourceImplementation(get()) }

    single<RoutesRepository> { RoutesRepositoryImplementation(get()) }
    single<ProductsRepository> { ProductsRepositoryImplementation(get()) }
}