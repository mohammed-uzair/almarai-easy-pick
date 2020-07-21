package com.almarai.easypick.data_source.shared_preference

import androidx.annotation.StringDef

@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@StringDef
annotation class SharedPreferencesKeys {
    companion object {
        var NETWORK_CONFIGURATION_SERVER_IP = "NETWORK_CONFIGURATION_SERVER_IP"
        var NETWORK_CONFIGURATION_SERVER_PORT = "NETWORK_CONFIGURATION_SEVER_PORT"
        var NETWORK_CONFIGURATION_STATUS = "NETWORK_CONFIGURATION_STATUS"

        var SALES_DATE = "SALES_DATE"
        var DEPOT_CODE = "DEPOT_CODE"
        var ROUTE_PREFERENCE = "ROUTE_PREFERENCE"
        var DATA_CONFIGURATION_STATUS = "DATA_CONFIGURATION_STATUS"

        var USER_LOGGED_USER_NAME = "USER_LOGGED_USER_NAME"
        var USER_LOGGED_USER_ID = "USER_LOGGED_USER_ID"
        var USER_LOGGED_IN_STATUS = "USER_LOGGED_IN_STATUS"
    }
}