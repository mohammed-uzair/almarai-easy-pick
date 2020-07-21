package com.almarai.easypick.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.almarai.data.easy_pick_models.Result
import com.almarai.data.easy_pick_models.route.Route
import com.almarai.data.easy_pick_models.route.RouteServiceStatus
import com.almarai.data.easy_pick_models.filter.Filters
import com.almarai.data.easy_pick_models.route.RouteAccessibility
import com.almarai.data.easy_pick_models.route.RouteStatus
import com.almarai.data.easy_pick_models.util.ERROR_OCCURRED
import com.almarai.data.easy_pick_models.util.exhaustive
import com.almarai.repository.api.RoutesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RouteSelectionViewModel(private val repository: RoutesRepository) : ViewModel() {
    private val _routes = MutableLiveData<Result<List<Route>>>()
    private val _routesStatus = MutableLiveData<Result<List<RouteServiceStatus>>>()
    private val _routeAccessibility = MutableLiveData<Result<RouteAccessibility>>()
    val routes: LiveData<Result<List<Route>>> = _routes
    val routesServiceStatus: LiveData<Result<List<RouteServiceStatus>>> = _routesStatus
    val routeAccessibility: LiveData<Result<RouteAccessibility>> = _routeAccessibility
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
                _routes.postValue(Result.Success(repository.getAllRoutes()))
            } catch (exception: Exception) {
                _routes.postValue(Result.Error(exception.message ?: ERROR_OCCURRED))
            }
        }
    }

    fun getAllRouteStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            _routesStatus.postValue(Result.Fetching)
            try {
                _routesStatus.postValue(Result.Success(repository.getAllRoutesStatus()))
            } catch (exception: Exception) {
                _routesStatus.postValue(Result.Error(exception.message ?: ERROR_OCCURRED))
            }
        }
    }

    fun getRouteStatus(routeNumber: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _routeAccessibility.postValue(Result.Fetching)
            try {
                _routeAccessibility.postValue(Result.Success(repository.getRouteStatus(routeNumber)))
            } catch (exception: Exception) {
                _routeAccessibility.postValue(Result.Error(exception.message ?: ERROR_OCCURRED))
            }
        }
    }

    fun setRouteServiceDetails(list: List<Route>) {
        var servedRoutes = 0
        var servingRoutes = 0
        var notServedRoutes = 0
        list.forEach {
            when (it.serviceStatus) {
                RouteStatus.Served -> ++servedRoutes
                RouteStatus.Serving -> ++servingRoutes
                RouteStatus.NotServed -> ++notServedRoutes
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
                    element.serviceStatus = details.second
                    break
                }
            }
        }
        return position
    }

    fun serveRoute(routeNumber:Int){

    }
}