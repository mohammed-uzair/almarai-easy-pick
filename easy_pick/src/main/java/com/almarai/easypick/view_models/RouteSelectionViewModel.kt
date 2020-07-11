package com.almarai.easypick.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.almarai.data.easy_pick_models.Result
import com.almarai.data.easy_pick_models.Route
import com.almarai.data.easy_pick_models.RouteStatus
import com.almarai.data.easy_pick_models.filter.Filters
import com.almarai.data.easy_pick_models.util.ERROR_OCCURRED
import com.almarai.data.easy_pick_models.util.exhaustive
import com.almarai.repository.api.RoutesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RouteSelectionViewModel(private val repository: RoutesRepository) : ViewModel() {
    private val _routes = MutableLiveData<Result<List<Route>>>()
    val routes: LiveData<Result<List<Route>>> = _routes
    internal var filtersModel: Filters? = null
    val totalRoutes: MutableLiveData<Int> = MutableLiveData(0)
    val routesServed: MutableLiveData<Int> = MutableLiveData(0)
    val routesServing: MutableLiveData<Int> = MutableLiveData(0)
    val notServed: MutableLiveData<Int> = MutableLiveData(0)
    val isFiltered = MutableLiveData(false)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _routes.postValue(Result.Fetching)
            try {
                _routes.postValue(Result.Success(repository.getAllRoutes(1, "", 0)))
            } catch (exception: Exception) {
                _routes.postValue(Result.Error(exception.message ?: ERROR_OCCURRED))
            }
        }
    }

    fun setRouteServiceDetails(list: List<Route>) {
        var servedRoutes = 0
        var servingRoutes = 0
        var notServedRoutes = 0
        list.forEach {
            when (it.serviceCurrentStatus) {
                is RouteStatus.Served -> ++servedRoutes
                is RouteStatus.Serving -> ++servingRoutes
                is RouteStatus.NotServed -> ++notServedRoutes
            }.exhaustive
        }

        totalRoutes.value = list.size
        routesServed.value = servedRoutes
        routesServing.value = servingRoutes
        notServed.value = notServedRoutes
    }

    fun updateRouteProcessed(details: Pair<Int, RouteStatus>): Int {
        var position = -1
        val data = _routes.value
        if (data is Result.Success<List<Route>>) {
            val routes = data.data
            for ((index, element) in routes.withIndex()) {
                if (element.number == details.first) {
                    position = index
                    element.serviceCurrentStatus = details.second
                    break
                }
            }
        }
        return position
    }
}