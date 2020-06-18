package com.almarai.easypick.data_source.firebase.interfaces

import androidx.lifecycle.MutableLiveData
import com.almarai.data.easy_pick_models.Route

interface FirebaseRoutesDataSource {
    fun getAllRoutes(
        depotCode: Int,
        salesDate: String,
        routesPreferences: Int = 0,
        mutableRoutes: MutableLiveData<List<Route>>
    )
}