package com.almarai.easypick.data_source.request

import android.os.Build
import com.almarai.data.BuildConfig

data class RequestHeader(
    val depotCode: String = "0",
    val salesDate: String = "",
    val routePreference: String = "NA",
    val appVersion: String = BuildConfig.VERSION_CODE.toString(),
    val appBuildType: String = BuildConfig.BUILD_TYPE,
    val deviceOsVersion: String = Build.VERSION.RELEASE,
    val deviceSdkVersion: String = Build.VERSION.SDK_INT.toString(),
    val deviceName: String = Build.MODEL,
    val deviceManufacturer: String = Build.MANUFACTURER,
    val deviceSerialNumber: String = ""
)