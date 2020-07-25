package com.almarai.easypick.data_source.local_data_source

import androidx.annotation.StringDef

@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@StringDef
annotation class SharedPreferencesKeys {
    companion object {
        const val NETWORK_CONFIGURATION_SERVER_IP = "NETWORK_CONFIGURATION_SERVER_IP"
        const val NETWORK_CONFIGURATION_SERVER_PORT = "NETWORK_CONFIGURATION_SEVER_PORT"
        const val NETWORK_CONFIGURATION_STATUS = "NETWORK_CONFIGURATION_STATUS"

        const val SALES_DATE = "SALES_DATE"
        const val DEPOT_CODE = "DEPOT_CODE"
        const val ROUTE_PREFERENCE = "ROUTE_PREFERENCE"
        const val DATA_CONFIGURATION_STATUS = "DATA_CONFIGURATION_STATUS"

        const val USER_LOGGED_USER_NAME = "USER_LOGGED_USER_NAME"
        const val USER_LOGGED_USER_ID = "USER_LOGGED_USER_ID"
        const val USER_LOGGED_IN_STATUS = "USER_LOGGED_IN_STATUS"

        const val APP_UPDATE = "APP_UPDATE"
        const val APP_UPDATE_DENY_COUNTER = "APP_UPDATE_DENY_COUNTER"
        const val APP_UPDATE_ELAPSED_TIMER = "APP_UPDATE_ELAPSED_TIMER"

        const val IS_ON_BOARDING_COMPLETED = "IS_ON_BOARDING_COMPLETED"
    }
}