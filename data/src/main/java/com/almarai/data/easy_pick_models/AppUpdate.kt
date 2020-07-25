package com.almarai.data.easy_pick_models

data class AppUpdate(
    val appVersionNumber: Int,
    val updateDescription: String,
    val isMandatoryUpdate: Boolean,
    val isForceUpdate: Boolean,
    val intermediateRelaxTime: Long
)