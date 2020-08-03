package com.almarai.easypick.data_source.di

//import org.koin.dsl.module

//val DataSourceModule = module {
//    //region Data source
//    single { Gson() }
//    single<SharedPreferenceDataSource> { SharedPreferenceDataSourceImplementation(get(), get()) }
//    single { WebService(get(), get(), get()) }
//    single<WebAppUpdateDataSource> { WebAppUpdateDataSourceImplementation(get()) }
//    single<LocalAppUpdateDataSource> { LocalAppUpdateDataSourceImplementation(get()) }
//    single<WebRoutesDataSource> { WebRoutesDataSourceImplementation(get()) }
//    single<WebProductsDataSource> { WebProductsDataSourceImplementation(get()) }
//    single<WebStatisticsDataSource> { WebStatisticsDataSourceImplementation(get()) }
//    single<FirebaseRoutesDataSource> { FirebaseRoutesDataSourceImplementation(get()) }
//    //endregion
//}