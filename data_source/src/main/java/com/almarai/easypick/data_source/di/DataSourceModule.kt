package com.almarai.easypick.data_source.di

import com.almarai.easypick.data_source.firebase.implementation.FirebaseRoutesDataSourceImplementation
import com.almarai.easypick.data_source.firebase.interfaces.FirebaseRoutesDataSource
import com.almarai.easypick.data_source.local_data_source.implementations.LocalAppUpdateDataSourceImplementation
import com.almarai.easypick.data_source.local_data_source.implementations.SharedPreferenceDataSourceImplementation
import com.almarai.easypick.data_source.local_data_source.interfaces.LocalAppUpdateDataSource
import com.almarai.easypick.data_source.local_data_source.interfaces.SharedPreferenceDataSource
import com.almarai.easypick.data_source.web.WebService
import com.almarai.easypick.data_source.web.implementation.WebAppUpdateDataSourceImplementation
import com.almarai.easypick.data_source.web.implementation.WebProductsDataSourceImplementation
import com.almarai.easypick.data_source.web.implementation.WebRoutesDataSourceImplementation
import com.almarai.easypick.data_source.web.implementation.WebStatisticsDataSourceImplementation
import com.almarai.easypick.data_source.web.interfaces.WebAppUpdateDataSource
import com.almarai.easypick.data_source.web.interfaces.WebProductsDataSource
import com.almarai.easypick.data_source.web.interfaces.WebRoutesDataSource
import com.almarai.easypick.data_source.web.interfaces.WebStatisticsDataSource
import com.google.gson.Gson
import org.koin.dsl.module

val DataSourceModule = module {
    //region Data source
    single { Gson() }
    single<SharedPreferenceDataSource> { SharedPreferenceDataSourceImplementation(get(), get()) }
    single { WebService(get(), get(), get()) }
    single<WebAppUpdateDataSource> { WebAppUpdateDataSourceImplementation(get()) }
    single<LocalAppUpdateDataSource> { LocalAppUpdateDataSourceImplementation(get()) }
    single<WebRoutesDataSource> { WebRoutesDataSourceImplementation(get()) }
    single<WebProductsDataSource> { WebProductsDataSourceImplementation(get()) }
    single<WebStatisticsDataSource> { WebStatisticsDataSourceImplementation(get()) }
    single<FirebaseRoutesDataSource> { FirebaseRoutesDataSourceImplementation(get()) }
    //endregion
}