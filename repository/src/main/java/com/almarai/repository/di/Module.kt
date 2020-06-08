package com.almarai.repository.di

import com.almarai.data_source.shared_preference.implementations.SharedPreferenceDataSourceImplementation
import com.almarai.data_source.shared_preference.interfaces.SharedPreferenceDataSource
import com.almarai.data_source.web.WebService
import com.almarai.data_source.web.implementation.WebProductsDataSourceImplementation
import com.almarai.data_source.web.implementation.WebRoutesDataSourceImplementation
import com.almarai.data_source.web.implementation.WebStatisticsDataSourceImplementation
import com.almarai.data_source.web.interfaces.WebProductsDataSource
import com.almarai.data_source.web.interfaces.WebRoutesDataSource
import com.almarai.data_source.web.interfaces.WebStatisticsDataSource
import com.almarai.repository.api.ApplicationRepository
import com.almarai.repository.api.ProductsRepository
import com.almarai.repository.api.RoutesRepository
import com.almarai.repository.api.StatisticsRepository
import com.almarai.repository.implementations.ApplicationRepositoryImplementation
import com.almarai.repository.implementations.ProductsRepositoryImplementation
import com.almarai.repository.implementations.RoutesRepositoryImplementation
import com.almarai.repository.implementations.StatisticsRepositoryImplementation
import org.koin.dsl.module

val RepositoryModule = module {
    single<SharedPreferenceDataSource> {
        SharedPreferenceDataSourceImplementation(
            get()
        )
    }

    single { WebService() }

    single<WebRoutesDataSource> {
        WebRoutesDataSourceImplementation(
            get()
        )
    }

    single<WebProductsDataSource> {
        WebProductsDataSourceImplementation(
            get()
        )
    }

    single<WebStatisticsDataSource> {
        WebStatisticsDataSourceImplementation(
            get()
        )
    }

    single<ApplicationRepository> { ApplicationRepositoryImplementation(get()) }
    single<RoutesRepository> { RoutesRepositoryImplementation(get()) }
    single<ProductsRepository> { ProductsRepositoryImplementation(get()) }
    single<StatisticsRepository> { StatisticsRepositoryImplementation(get()) }
}