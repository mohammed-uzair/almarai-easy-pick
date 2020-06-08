package com.almarai.repository.utils

import androidx.annotation.StringDef

@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@StringDef
annotation class SharedPreferencesKeys {
    companion object {
        var NETWORK_CONFIGURATION_SERVER_IP = "NETWORK_CONFIGURATION_SERVER_IP"
        var NETWORK_CONFIGURATION_SERVER_PORT = "NETWORK_CONFIGURATION_SEVER_PORT"
        var NETWORK_CONFIGURATION_STATUS = "NETWORK_CONFIGURATION_STATUS"

        var DATA_CONFIGURATION_SALES_DATE = "DATA_CONFIGURATION_SALES_DATE"
        var DATA_CONFIGURATION_DEPOT_CODE = "DATA_CONFIGURATION_DEPOT_CODE"
        var DATA_CONFIGURATION_ROUTE_PREFERENCE = "DATA_CONFIGURATION_ROUTE_PREFERENCE"
        var DATA_CONFIGURATION_STATUS = "DATA_CONFIGURATION_STATUS"

        var USER_LOGGED_USER_NAME = "USER_LOGGED_USER_NAME"
        var USER_LOGGED_USER_ID = "USER_LOGGED_USER_ID"
        var USER_LOGGED_IN_STATUS = "USER_LOGGED_IN_STATUS"
    }
}