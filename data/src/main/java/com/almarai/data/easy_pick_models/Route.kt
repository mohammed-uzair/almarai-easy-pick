package com.almarai.data.easy_pick_models

import android.os.Parcelable
import androidx.annotation.IntDef
import com.almarai.data.easy_pick_models.util.exhaustive
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Route(
    val number: Int,
    val description: String,
    val descriptionArabic: String,
    val group: List<GroupType>,
    val serviceStatus: RouteServiceStatus
) : Parcelable {
    var serviceCurrentStatus: @RawValue RouteStatus = RouteStatus.NotServed
        get() {
            //Note: Do not remove this below line, this is not a correct warning, will break the code if removed
            if (field == null) {
                field = when (serviceStatus) {
                    RouteServiceStatus.NotServed -> RouteStatus.NotServed
                    RouteServiceStatus.Serving -> RouteStatus.Serving
                    RouteServiceStatus.Served -> RouteStatus.Served
                }.exhaustive
            }
            return field
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

enum class RouteServiceStatus {
    NotServed,
    Serving,
    Served
}

enum class GroupType {
    Bakery,
    Poultry,
    Dairy,
    IPNC,
    NonIPNC,
    Customer
}

sealed class RouteStatus {
    object Served : RouteStatus()
    object Serving : RouteStatus()
    object NotServed : RouteStatus()
}