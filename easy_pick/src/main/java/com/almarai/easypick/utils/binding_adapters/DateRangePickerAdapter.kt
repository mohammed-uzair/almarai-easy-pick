package com.almarai.easypick.utils.binding_adapters

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.almarai.easypick.utils.AppDateTimeFormat
import com.almarai.easypick.utils.DateUtil

@SuppressLint("SetTextI18n")
@BindingAdapter("dateRangePicker")
fun TextView.bindDateRangePicker(dateRange: String) {
    val dates = dateRange.split("-")

    this.text =
        DateUtil.getDate(dates[0].trim().toLong(), AppDateTimeFormat.formatDDMMYYYY) +
                " - " +
                DateUtil.getDate(dates[1].trim().toLong(), AppDateTimeFormat.formatDDMMYYYY)
}