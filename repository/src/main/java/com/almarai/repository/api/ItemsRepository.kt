package com.almarai.repository.api

import androidx.lifecycle.MutableLiveData
import com.almarai.data.easy_pick_models.Item

interface ItemsRepository {
    fun getAllItems(
        depotCode: Int,
        salesDate: String,
        routeNumber: Int,
        routesPreferences: Int = 0,
        mutableItems: MutableLiveData<List<Item>>
    )
}