package com.almarai.data.easy_pick_models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Route(
    val number: Int,
    val description: String,
    val descriptionArabic: String,
    val group: Int = 0,
    val serviceStatus: Int
) : Parcelable