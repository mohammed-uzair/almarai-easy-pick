package com.almarai.data.easy_pick_models.adapters

import com.almarai.data.easy_pick_models.Group
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class JsonParsingGroupAdapter {
    @ToJson
    fun toJson(group: Group) = group.toString()

    @FromJson
    fun fromJson(group: String?): Group? {
        if (group == null) return null

        return when (group) {
            "Bakery" -> Group.Bakery
            "Poultry" -> Group.Poultry
            "Dairy" -> Group.Dairy
            "IPNC" -> Group.IPNC
            "NonIPNC" -> Group.NonIPNC
            "Customer" -> Group.Customer
            else -> null
        }
    }
}