package com.almarai.easypick.di.hilt

import android.content.Context
import com.almarai.easypick.data_source.AppDataSourceType
import com.almarai.easypick.data_source.AppDataSourceTypes
import com.almarai.easypick.data_source.firebase.implementation.FirebaseProductsDataSourceImplementation
import com.almarai.easypick.data_source.firebase.implementation.FirebaseRoutesDataSourceImplementation
import com.almarai.easypick.data_source.firebase.implementation.FirebaseTicketDataSourceImplementation
import com.almarai.easypick.data_source.interfaces.*
import com.almarai.easypick.data_source.local_data_source.implementations.AppUpdateDataSourceImplementation
import com.almarai.easypick.data_source.local_data_source.implementations.SharedPreferenceDataSourceImplementation
import com.almarai.easypick.data_source.web.WebService
import com.almarai.easypick.data_source.web.implementation.WebAppUpdateDataSourceImplementation
import com.almarai.easypick.data_source.web.implementation.WebProductsDataSourceImplementation
import com.almarai.easypick.data_source.web.implementation.WebRoutesDataSourceImplementation
import com.almarai.easypick.data_source.web.implementation.WebStatisticsDataSourceImplementation
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DataSourceModule {
    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context, gson: Gson): SharedPreferenceDataSource {
        return SharedPreferenceDataSourceImplementation(context, gson)
    }

    @Provides
    @Singleton
    fun provideAppUpdateDataSource(
        appDataSourceType: AppDataSourceType,
        sharedPreferenceDataSource: SharedPreferenceDataSource
    ): AppUpdateDataSource {
        return when (appDataSourceType.getAppUpdateType()) {
            AppDataSourceTypes.Almarai -> AppUpdateDataSourceImplementation(
                sharedPreferenceDataSource
            )
            else -> WebAppUpdateDataSourceImplementation()
        }
    }

    @Provides
    fun provideRoutesDataSource(
        context: Context,
        webservice: WebService,
        appDataSourceType: AppDataSourceType,
        gson: Gson,
        sharedPreferenceDataSource: SharedPreferenceDataSource
    ): RouteDataSource {
        return when (appDataSourceType.getAppUpdateType()) {
            AppDataSourceTypes.Almarai -> WebRoutesDataSourceImplementation(webservice)
            else -> FirebaseRoutesDataSourceImplementation(
                context,
                gson,
                sharedPreferenceDataSource
            )
        }
    }

    @Provides
    @Singleton
    fun provideProductsDataSource(
        context: Context,
        webservice: WebService,
        appDataSourceType: AppDataSourceType,
        gson: Gson,
        sharedPreferenceDataSource: SharedPreferenceDataSource
    ): ProductsDataSource {
        return when (appDataSourceType.getAppUpdateType()) {
            AppDataSourceTypes.Almarai -> WebProductsDataSourceImplementation(webservice)
            else -> FirebaseProductsDataSourceImplementation(
                context,
                gson,
                sharedPreferenceDataSource
            )
        }
    }

    @Provides
    fun provideStatisticsDataSource(
        context: Context,
        webservice: WebService,
        appDataSourceType: AppDataSourceType,
        sharedPreferenceDataSource: SharedPreferenceDataSource
    ): StatisticsDataSource {
//        return when (appDataSourceType.getAppUpdateType()) {
//            AppDataSourceType.Almarai ->
        return WebStatisticsDataSourceImplementation(webservice)
//            else -> FirebaseStatisticsDataSourceImplementation(context, sharedPreferenceDataSource)
//        }
    }

    @Provides
    @Singleton
    fun provideTicketDataSource(
        context: Context,
        webservice: WebService,
        appDataSourceType: AppDataSourceType,
        sharedPreferenceDataSource: SharedPreferenceDataSource
    ): TicketDataSource {
//        return when (appDataSourceType.getAppUpdateType()) {
//            AppDataSourceType.Almarai -> WebFileUploadDataSourceImplementation()
//            else ->
        return FirebaseTicketDataSourceImplementation()
//        }
    }
}