package com.almarai.easypick.di.hilt

import com.almarai.easypick.data_source.firebase.implementation.FirebaseFileUploadDataSourceImplementation
import com.almarai.easypick.data_source.firebase.interfaces.FirebaseFileUploadDataSource
import com.almarai.easypick.data_source.local_data_source.implementations.LocalAppUpdateDataSourceImplementation
import com.almarai.easypick.data_source.local_data_source.implementations.SharedPreferenceDataSourceImplementation
import com.almarai.easypick.data_source.local_data_source.interfaces.LocalAppUpdateDataSource
import com.almarai.easypick.data_source.local_data_source.interfaces.SharedPreferenceDataSource
import com.almarai.easypick.data_source.web.implementation.WebAppUpdateDataSourceImplementation
import com.almarai.easypick.data_source.web.implementation.WebProductsDataSourceImplementation
import com.almarai.easypick.data_source.web.implementation.WebRoutesDataSourceImplementation
import com.almarai.easypick.data_source.web.implementation.WebStatisticsDataSourceImplementation
import com.almarai.easypick.data_source.web.interfaces.WebAppUpdateDataSource
import com.almarai.easypick.data_source.web.interfaces.WebProductsDataSource
import com.almarai.easypick.data_source.web.interfaces.WebRoutesDataSource
import com.almarai.easypick.data_source.web.interfaces.WebStatisticsDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
abstract class DataSourceModule {
    @Binds
    abstract fun provideSharedPreferences(sharedPreferenceDataSourceImplementation: SharedPreferenceDataSourceImplementation): SharedPreferenceDataSource

    @Binds
    abstract fun provideLocalAppUpdateDataSource(localAppUpdateDataSourceImplementation: LocalAppUpdateDataSourceImplementation): LocalAppUpdateDataSource

    @Binds
    abstract fun provideWebAppUpdateDataSource(webAppUpdateDataSourceImplementation: WebAppUpdateDataSourceImplementation): WebAppUpdateDataSource

    @Binds
    abstract fun provideWebRoutesDataSource(webRoutesDataSourceImplementation: WebRoutesDataSourceImplementation): WebRoutesDataSource

    @Binds
    abstract fun provideWebProductsDataSource(webProductsDataSourceImplementation: WebProductsDataSourceImplementation): WebProductsDataSource

    @Binds
    abstract fun provideWebStatisticsDataSource(webStatisticsDataSourceImplementation: WebStatisticsDataSourceImplementation): WebStatisticsDataSource

    @Binds
    abstract fun provideFirebaseFileUploadDataSource(fileUploadDataSourceImplementation: FirebaseFileUploadDataSourceImplementation): FirebaseFileUploadDataSource
}