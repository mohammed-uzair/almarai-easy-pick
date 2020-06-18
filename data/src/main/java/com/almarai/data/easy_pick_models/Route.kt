package com.almarai.data.easy_pick_models

import android.os.Parcelable
import androidx.annotation.IntDef
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Route(
    val number: Int,
    val description: String,
    val descriptionArabic: String,
    val group: Int = 0,
    val serviceStatus: Int = 0
) : Parcelable {
    var serviceCurrentStatus: @RawValue RouteStatus = RouteStatus.NotServed
        get() = when (serviceStatus) {
            ServiceStatus.SERVED -> RouteStatus.Served
            ServiceStatus.SERVING -> RouteStatus.Serving
            ServiceStatus.NOT_SERVED -> RouteStatus.NotServed
            else -> RouteStatus.NotServed
        }
}

@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@IntDef
annotation class ServiceStatus {
    companion object {
        var NOT_SERVED = 0
        var SERVING = 1
        var SERVED = 2
    }
}

sealed class RouteStatus {
    object Served : RouteStatus()
    object Serving : RouteStatus()
    object NotServed : RouteStatus()
}