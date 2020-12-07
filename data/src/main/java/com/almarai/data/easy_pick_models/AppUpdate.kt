package com.almarai.data.easy_pick_models

data class AppUpdate(
    val appVersionNumber: Int = 0,
    val updateDescription: String = "",
    val isMandatoryUpdate: Boolean = false,
    val isForceUpdate: Boolean = false,
    val intermediateRelaxTime: Long = 0
)