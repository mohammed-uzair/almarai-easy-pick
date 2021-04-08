package com.almarai.easypick.views.utils.binding_adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.almarai.easypick.BuildConfig

@BindingAdapter("appVersionName")
fun TextView.bindAppVersionName(versionName: String) {
    //Set the app version
    var appVersionName = ""
    val buildType = BuildConfig.BUILD_TYPE

    //Add the first alphabet of the build type in front of version name i.e -> //D-1.0.0 D
    if (buildType == "Development" || buildType == "debug") {
        appVersionName = "D-${BuildConfig.VERSION_NAME}"
    } else if (buildType == "release") {
        appVersionName = "R-${BuildConfig.VERSION_NAME}"
    } else if (buildType == "UAT") {
        appVersionName = "U-${BuildConfig.VERSION_NAME}"
    }

    //Add a 'D' at the end of version name if this build is debuggable
    if (BuildConfig.DEBUG) {
        appVersionName += " D"
    }

    text = appVersionName
}