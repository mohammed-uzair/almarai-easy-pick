package com.almarai.easypick.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.almarai.data.easy_pick_models.Route
import com.almarai.repository.api.RoutesRepository
import com.almarai.repository.api_implementations.RoutesRepositoryImplementation
import org.koin.core.KoinComponent
import org.koin.core.inject

class RouteSelectionViewModel : ViewModel(), KoinComponent {
    private val repository: RoutesRepository by inject()

    val mutableRoutes = MutableLiveData<List<Route>>()

    init {
        repository.getAllRoutes(1, "", 0, mutableRoutes)
    }

    fun getRouteServiceDetails(list: List<Route>): RoutesServiceDetail {
        return RoutesServiceDetail(123, 24, list.size)
    }
}

data class RoutesServiceDetail(val servedRoute: Int, val servingRoutes: Int, val totalRoutes: Int)