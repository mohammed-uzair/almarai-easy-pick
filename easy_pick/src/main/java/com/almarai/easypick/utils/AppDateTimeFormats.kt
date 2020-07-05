package com.almarai.easypick.utils

import androidx.annotation.StringDef

@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@StringDef
annotation class AppDateTimeFormat {
    companion object {
        const val formatDDMMMYYYY = "dd-MMM-yyyy" //02-Feb-2018
        const val formatDDMMYYYY = "dd/MM/yyyy" //02-08-2017
        const val formatDDMMYY = "dd/MM/yy" //02/02/2018
        const val formatMMDDYYHHMM = "MM/dd/yy HH:mm" //02/02/2018 12:22
        const val formatEEEEDDMMYYYY = "EEEE dd-MM-yyyy" //Monday 12-02-2018
        const val formatDD_MM_YY = "dd-MM-yy" //02-08-17
        const val formatDDMMYYYYHHMMSSA = "dd-MMM-yyyy hh:mm:ss a"
        const val formatDDMMYYYYHHMMSS = "dd-MMM-yyyy HH:mm:ss" //02-Aug-2018
        const val formatDDMMYYYYTHHMMSS = "yyyy-MM-dd'T'HH:mm:ss" //2018-12-25T12:06:32
        const val formatMMDDYYYY = "MM/dd/yyyy" //12/25/2018
    }
}