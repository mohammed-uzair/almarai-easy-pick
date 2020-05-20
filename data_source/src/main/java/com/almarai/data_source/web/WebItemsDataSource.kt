package com.almarai.data_source.web

import androidx.lifecycle.MutableLiveData
import com.almarai.data.easy_pick_models.Item

interface WebItemsDataSource {
    fun getAllItems(
        depotCode: Int,
        salesDate: String,
        routeNumber: Int,
        routesPreferences: Int = 0,
        mutableItems: MutableLiveData<List<Item>>
    )
}