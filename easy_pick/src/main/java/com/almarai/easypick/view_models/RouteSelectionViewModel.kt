package com.almarai.easypick.view_models

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.almarai.common.utils.Logger
import com.almarai.data.easy_pick_models.Result
import com.almarai.data.easy_pick_models.filter.Filters
import com.almarai.data.easy_pick_models.route.Route
import com.almarai.data.easy_pick_models.route.RouteAccessibility
import com.almarai.data.easy_pick_models.route.RouteServiceStatus
import com.almarai.data.easy_pick_models.route.RouteStatus
import com.almarai.data.easy_pick_models.util.ERROR_OCCURRED
import com.almarai.repository.api.RoutesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RouteSelectionViewModel @ViewModelInject constructor(private val repository: RoutesRepository) :
    ViewModel() {
    companion object {
        const val TAG = "RouteSelectionViewModel"
    }

    private val _routes = MutableLiveData<Result<List<Route>>>()
    val routes: LiveData<Result<List<Route>>> = _routes

    private val _routesStatus = MutableLiveData<Result<List<RouteServiceStatus>>>()
    val routesServiceStatus: LiveData<Result<List<RouteServiceStatus>>> = _routesStatus

    private val _routeAccessibility = MutableLiveData<Result<RouteAccessibility>>()
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
                val data = repository.getAllRoutes()
                data.collect {
                    _routes.postValue(Result.Success(it))
                }
            } catch (exception: Exception) {
                _routes.postValue(Result.Error(exception.message ?: ERROR_OCCURRED))
            }
        }
    }

    fun getAllRouteStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            _routesStatus.postValue(Result.Fetching)
            try {
                repository.getAllRoutesStatus().collect {
                    if (it.isNotEmpty()) {
                        _routesStatus.postValue(Result.Success(it))
                    }
                }
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

    fun updateRouteStatus(routeNumber: Int) = viewModelScope.launch(Dispatchers.IO) {
        try {
            repository.updateRouteStatus(routeNumber, RouteStatus.Serving)
        } catch (exception: Exception) {
            Logger.logError(
                TAG, exception.message ?: "", exception
            )
        }
    }

    fun setRouteServiceDetails(list: List<Route>) {
        var servedRoutes = 0
        var servingRoutes = 0
        var notServedRoutes = 0
        list.forEach {
//            when (it.routeStatus) {
//                RouteStatus.Served -> ++servedRoutes
//                RouteStatus.PartialServed -> ++servedRoutes
//                RouteStatus.Serving -> ++servingRoutes
//                RouteStatus.NotServed -> ++notServedRoutes
//            }.exhaustive
        }

        totalRoutes.value = list.size
        routesServed.value = servedRoutes
        routesServing.value = servingRoutes
        notServed.value = notServedRoutes
    }
}