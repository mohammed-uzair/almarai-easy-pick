package com.almarai.repository.api_implementations

import androidx.lifecycle.MutableLiveData
import com.almarai.data.easy_pick_models.Item
import com.almarai.data_source.web.WebItemsDataSource
import com.almarai.repository.api.ItemsRepository

class ItemsRepositoryImplementation(private val webItemsDataSource: WebItemsDataSource) :
    ItemsRepository {
    override fun getAllItems(
        depotCode: Int,
        salesDate: String,
        routeNumber: Int,
        routesPreferences: Int,
        mutableItems: MutableLiveData<List<Item>>
    ) {
        webItemsDataSource.getAllItems(depotCode, salesDate, routeNumber, routesPreferences, mutableItems)
    }
}