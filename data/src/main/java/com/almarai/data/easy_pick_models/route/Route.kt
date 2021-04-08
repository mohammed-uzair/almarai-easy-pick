package com.almarai.data.easy_pick_models.route

import com.almarai.data.easy_pick_models.Group
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Route(
    var number: Int = 0,
    val description: String = "NA",
    var translatedDescription: String = description,
    val group: List<Group> = listOf(),
    var routeStatus: RouteStatus = RouteStatus.NotServed
)