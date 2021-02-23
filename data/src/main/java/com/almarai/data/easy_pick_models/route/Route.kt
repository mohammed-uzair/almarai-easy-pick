package com.almarai.data.easy_pick_models.route

import com.almarai.data.easy_pick_models.GroupType
import com.almarai.data.easy_pick_models.util.exhaustive

class Route(
    var number: Int = 0,
    val description: String = "NA",
    var translatedDescription: String = description,
    val group: List<GroupType> = listOf(),
    private val routeStatus: com.almarai.data.easy_pick_models.web.enum.RouteStatus = com.almarai.data.easy_pick_models.web.enum.RouteStatus.NotServed
) {
    var status: RouteStatus = RouteStatus.NotServed
        get() {
            return when (routeStatus.name) {
                com.almarai.data.easy_pick_models.web.enum.RouteStatus.Served.name -> RouteStatus.Served
                com.almarai.data.easy_pick_models.web.enum.RouteStatus.NotServed.name -> RouteStatus.NotServed
                com.almarai.data.easy_pick_models.web.enum.RouteStatus.PartiallyServed.name -> RouteStatus.PartialServed
                else -> RouteStatus.NotServed
            }.exhaustive
        }
}