package com.almarai.data.easy_pick_models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Group(
    val isDairy: Boolean = true,
    val isPoultry: Boolean = true,
    val isBakery: Boolean = true,
    val isIpnc: Boolean = true,
    val isNonIpnc: Boolean = true,
    val isTc: Boolean = true,
    val isNonTc: Boolean = true,
    val isCustomerOnly: Boolean = true
) : Parcelable