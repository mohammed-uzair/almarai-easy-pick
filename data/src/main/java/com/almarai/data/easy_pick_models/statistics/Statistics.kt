package com.almarai.data.easy_pick_models.statistics

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Statistics(
    val numberOfPhysicalPapersSaved: Long,
    val statisticsData: List<StatisticsData>
) : Parcelable