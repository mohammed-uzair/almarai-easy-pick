package com.almarai.data.easy_pick_models.route

import android.os.Parcelable
import com.almarai.data.easy_pick_models.GroupType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Route(
    var number: Int = 0,
    val description: String = "NA",
    var translatedDescription: String = description,
    val group: List<GroupType> = listOf(),
    var serviceStatus: RouteStatus = RouteStatus.NotServed
) : Parcelable