package com.almarai.easypick.data_source

import com.almarai.easypick.data_source.interfaces.SharedPreferenceDataSource
import javax.inject.Inject

sealed class AppDataSourceTypes {
    object Almarai : AppDataSourceTypes()
    object Cloud : AppDataSourceTypes()
}

private const val DATA_SOURCE_PREFERENCE_KEY = "Data Source"

class AppDataSourceType @Inject constructor(private val sharedPreferenceDataSource: SharedPreferenceDataSource) {
    fun getAppUpdateType(): AppDataSourceTypes {
        val dataSource =
            sharedPreferenceDataSource.getSharedPreferenceString(DATA_SOURCE_PREFERENCE_KEY)

        return when (dataSource) {
            AppDataSourceTypes.Almarai.javaClass.simpleName -> AppDataSourceTypes.Almarai
            else -> AppDataSourceTypes.Cloud
        }
    }
}