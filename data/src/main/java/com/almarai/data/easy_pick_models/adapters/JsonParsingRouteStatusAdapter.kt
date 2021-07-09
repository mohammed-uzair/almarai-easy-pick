package com.almarai.data.easy_pick_models.adapters

import com.almarai.data.easy_pick_models.route.RouteStatus
import com.almarai.data.easy_pick_models.util.exhaustive
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class JsonParsingRouteStatusAdapter {
    @ToJson
    fun toJson(routeStatus: RouteStatus) =
        when (routeStatus) {
            is RouteStatus.Serving -> "Serving"
            is RouteStatus.NotServed -> "NotServed"
            is RouteStatus.PartialServed -> "PartialServed"
            is RouteStatus.Served -> "Served"
        }.exhaustive

    @FromJson
    fun fromJson(routeStatus: String?): RouteStatus {
        if (routeStatus == null) return RouteStatus.NotServed

        return when (routeStatus) {
            "NotServed" -> RouteStatus.NotServed
            "Serving" -> RouteStatus.Serving
            "Served" -> RouteStatus.Served
            "PartialServed" -> RouteStatus.PartialServed
            else -> RouteStatus.NotServed
        }.exhaustive
    }
}