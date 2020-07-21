package com.almarai.data.easy_pick_models.route

import android.os.Parcelable
import com.almarai.data.easy_pick_models.GroupType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Route(
    val number: Int,
    val description: String,
    val descriptionArabic: String,
    val group: List<GroupType> = listOf(),
    var serviceStatus: RouteStatus
) : Parcelable