package com.almarai.easypick.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.almarai.data.easy_pick_models.Result
import com.almarai.data.easy_pick_models.Route
import com.almarai.data.easy_pick_models.RouteStatus
import com.almarai.data.easy_pick_models.ERROR_OCCURRED
import com.almarai.repository.api.RoutesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RouteSelectionViewModel(private val repository: RoutesRepository) : ViewModel() {
    private val _routes = MutableLiveData<Result<List<Route>>>()
    val routes: LiveData<Result<List<Route>>> = _routes

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

    fun getRouteServiceDetails(list: List<Route>) = RoutesServiceDetail(123, 24, list.size)

    fun updateRouteProcessed(details: Pair<Int, RouteStatus>) {
        val data = _routes.value
        if (data is Result.Success<List<Route>>) {
            val routes = data.data
            routes.find { it.number == details.first }
                ?.apply { serviceCurrentStatus = details.second }
        }
    }

    data class RoutesServiceDetail(
        val servedRoute: Int,
        val servingRoutes: Int,
        val totalRoutes: Int
    )
}