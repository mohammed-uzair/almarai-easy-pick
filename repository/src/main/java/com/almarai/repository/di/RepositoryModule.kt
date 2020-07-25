package com.almarai.repository.di

import com.almarai.repository.api.*
import com.almarai.repository.implementations.*
import org.koin.dsl.module

val RepositoryModule = module {
    //region Repository
    single<AppUpdateRepository> { AppUpdateRepositoryImplementation(get(), get()) }
    single<ApplicationRepository> { ApplicationRepositoryImplementation(get()) }
    single<RoutesRepository> { RoutesRepositoryImplementation(get(), get()) }
    single<ProductsRepository> { ProductsRepositoryImplementation(get()) }
    single<StatisticsRepository> { StatisticsRepositoryImplementation(get()) }
    //endregion
}

val DataSourceModule = com.almarai.easypick.data_source.di.DataSourceModule