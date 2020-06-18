package com.almarai.repository.di

import com.almarai.easypick.data_source.firebase.implementation.FirebaseRoutesDataSourceImplementation
import com.almarai.easypick.data_source.firebase.interfaces.FirebaseRoutesDataSource
import com.almarai.easypick.data_source.shared_preference.implementations.SharedPreferenceDataSourceImplementation
import com.almarai.easypick.data_source.shared_preference.interfaces.SharedPreferenceDataSource
import com.almarai.easypick.data_source.web.WebService
import com.almarai.easypick.data_source.web.implementation.WebProductsDataSourceImplementation
import com.almarai.easypick.data_source.web.implementation.WebRoutesDataSourceImplementation
import com.almarai.easypick.data_source.web.implementation.WebStatisticsDataSourceImplementation
import com.almarai.easypick.data_source.web.interfaces.WebProductsDataSource
import com.almarai.easypick.data_source.web.interfaces.WebRoutesDataSource
import com.almarai.easypick.data_source.web.interfaces.WebStatisticsDataSource
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
    single { WebService() }

    //region Data source
    single<SharedPreferenceDataSource> { SharedPreferenceDataSourceImplementation(get()) }
    single<WebRoutesDataSource> { WebRoutesDataSourceImplementation(get()) }
    single<WebProductsDataSource> { WebProductsDataSourceImplementation(get()) }
    single<WebStatisticsDataSource> { WebStatisticsDataSourceImplementation(get()) }
    single<FirebaseRoutesDataSource> { FirebaseRoutesDataSourceImplementation(get()) }
    //endregion

    //region Repository
    single<ApplicationRepository> { ApplicationRepositoryImplementation(get()) }
    single<RoutesRepository> { RoutesRepositoryImplementation(get(), get()) }
    single<ProductsRepository> { ProductsRepositoryImplementation(get()) }
    single<StatisticsRepository> { StatisticsRepositoryImplementation(get()) }
    //endregion
}