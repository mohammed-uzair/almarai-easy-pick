package com.almarai.data.easy_pick_models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StatisticsData(val date: Float, val papersSaved: Long) : Parcelable

@Parcelize
data class Statistics(
    val numberOfPhysicalPapersSaved: Long,
    val statisticsData: List<StatisticsData>
) : Parcelable