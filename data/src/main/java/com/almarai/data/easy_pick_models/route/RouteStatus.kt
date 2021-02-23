package com.almarai.data.easy_pick_models.route

sealed class RouteStatus {
    object NotServed : RouteStatus()
    object Serving : RouteStatus()
    object Served : RouteStatus()
    object PartialServed : RouteStatus()
}