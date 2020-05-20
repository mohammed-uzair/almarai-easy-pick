package com.almarai.repository.di

import com.almarai.data_source.web.*
import com.almarai.repository.api.ItemsRepository
import com.almarai.repository.api.RoutesRepository
import com.almarai.repository.api_implementations.ItemsRepositoryImplementation
import com.almarai.repository.api_implementations.RoutesRepositoryImplementation
import org.koin.dsl.module

val RepositoryModule = module {
    single { WebService() }
    single<WebRoutesDataSource> { WebRoutesDataSourceImplementation(get()) }
    single<WebItemsDataSource> { WebItemsDataSourceImplementation(get()) }

    single<RoutesRepository> { RoutesRepositoryImplementation(get()) }
    single<ItemsRepository> { ItemsRepositoryImplementation(get()) }
}