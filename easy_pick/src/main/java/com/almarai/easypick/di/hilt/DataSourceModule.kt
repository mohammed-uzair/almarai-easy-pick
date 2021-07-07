package com.almarai.easypick.di.hilt

import android.content.Context
import com.almarai.common.app_update.AppUpdates
import com.almarai.easypick.data_source.AppDataSourceType
import com.almarai.easypick.data_source.AppDataSourceTypes
import com.almarai.easypick.data_source.firebase.implementation.*
import com.almarai.easypick.data_source.interfaces.*
import com.almarai.easypick.data_source.local_data_source.implementations.SharedPreferenceDataSourceImplementation
import com.almarai.easypick.data_source.web.WebService
import com.almarai.easypick.data_source.web.implementation.WebAppUpdateDataSourceImplementation
import com.almarai.easypick.data_source.web.implementation.WebProductsDataSourceImplementation
import com.almarai.easypick.data_source.web.implementation.WebRoutesDataSourceImplementation
import com.almarai.easypick.data_source.web.implementation.WebStatisticsDataSourceImplementation
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context, moshi: Moshi): SharedPreferenceDataSource {
        return SharedPreferenceDataSourceImplementation(context, moshi)
    }

    @Provides
    @Singleton
    fun provideAppUpdateDataSource(
        appDataSourceType: AppDataSourceType,
        webservice: WebService,
        appUpdates: AppUpdates
    ): AppUpdateDataSource {
        return when (appDataSourceType.getAppUpdateType()) {
            AppDataSourceTypes.Almarai -> WebAppUpdateDataSourceImplementation(
                webservice,
                appUpdates
            )
            else -> FirebaseAppUpdateDataSourceImplementation()
        }
    }

    @Provides
    fun provideRoutesDataSource(
        webservice: WebService,
        appDataSourceType: AppDataSourceType,
        moshi: Moshi,
        sharedPreferenceDataSource: SharedPreferenceDataSource
    ): RouteDataSource {
        return when (appDataSourceType.getAppUpdateType()) {
            AppDataSourceTypes.Almarai -> WebRoutesDataSourceImplementation(webservice)
            else -> FirebaseRoutesDataSourceImplementation(
                moshi, sharedPreferenceDataSource
            )
        }
    }

    @Provides
    @Singleton
    fun provideProductsDataSource(
        context: Context,
        webservice: WebService,
        appDataSourceType: AppDataSourceType,
        moshi: Moshi,
        sharedPreferenceDataSource: SharedPreferenceDataSource
    ): ProductsDataSource {
        return when (appDataSourceType.getAppUpdateType()) {
            AppDataSourceTypes.Almarai -> WebProductsDataSourceImplementation(webservice)
            else -> FirebaseProductsDataSourceImplementation(
                context,
                moshi,
                sharedPreferenceDataSource
            )
        }
    }

    @Provides
    fun provideStatisticsDataSource(
        webservice: WebService,
        appDataSourceType: AppDataSourceType,
        sharedPreferenceDataSource: SharedPreferenceDataSource,
        moshi: Moshi
    ): StatisticsDataSource {
        return when (appDataSourceType.getAppUpdateType()) {
            AppDataSourceTypes.Almarai ->
                return WebStatisticsDataSourceImplementation(webservice)
            else -> FirebaseStatisticsDataSourceImplementation(sharedPreferenceDataSource, moshi)
        }
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