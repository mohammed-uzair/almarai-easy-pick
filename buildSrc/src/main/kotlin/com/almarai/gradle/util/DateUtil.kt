package com.almarai.gradle.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

private fun getCurrentDateTime(): String {
    val format = "dd_MMM_yyyy_hh_mm_ss" //02_Dec_2023_12_22_36

    val dateFormat: DateFormat = SimpleDateFormat(format, Locale.ENGLISH)
    val calendar = Calendar.getInstance(Locale.ENGLISH)
    return dateFormat.format(calendar.time)
}