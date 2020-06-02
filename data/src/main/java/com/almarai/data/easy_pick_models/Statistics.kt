package com.almarai.data.easy_pick_models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Statistics(val physicalPagesSavedCount: Long = 0) : Parcelable

//https://pastebin.com/raw/sFKTen9b
/*
{
    "physicalPagesSavedCount": 12309
}
 */