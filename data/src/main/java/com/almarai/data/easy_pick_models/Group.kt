package com.almarai.data.easy_pick_models

sealed class Group {
    object Bakery : Group()
    object Poultry : Group()
    object Dairy : Group()
    object IPNC : Group()
    object NonIPNC : Group()
    object Customer : Group()
}