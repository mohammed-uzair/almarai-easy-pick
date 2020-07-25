package com.almarai.data.easy_pick_models.statistics

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StatisticsData(val date: Float, val papersSaved: Long) : Parcelable