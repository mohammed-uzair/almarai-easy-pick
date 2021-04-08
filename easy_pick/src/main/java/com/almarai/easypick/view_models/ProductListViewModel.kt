package com.almarai.easypick.view_models

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.almarai.data.easy_pick_models.Result
import com.almarai.data.easy_pick_models.filter.Filters
import com.almarai.data.easy_pick_models.product.Product
import com.almarai.data.easy_pick_models.product.ProductStatus
import com.almarai.data.easy_pick_models.route.RouteStatus
import com.almarai.data.easy_pick_models.util.ERROR_OCCURRED
import com.almarai.easypick.utils.Event
import com.almarai.repository.api.ProductsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProductListViewModel @ViewModelInject constructor(private val repository: ProductsRepository) :
    ViewModel() {
    private val _products = MutableLiveData<Result<List<Product>>>()
    private val _routeDataUpdated = MutableLiveData<Event<Result<RouteStatus>>>()
    internal val products: LiveData<Result<List<Product>>> = _products
    internal val routeDataUpdatedCategory: LiveData<Event<Result<RouteStatus>>> = _routeDataUpdated
    internal var filtersModel: Filters? = null
    val totalItems = MutableLiveData(0)
    val itemsPicked = MutableLiveData(0)
    val isFiltered = MutableLiveData(false)

    fun getAllProducts(routeNumber: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _products.postValue(Result.Fetching)
            try {
                repository.getAllProducts(routeNumber).collect {
                    _products.postValue(Result.Success(it))
                }
            } catch (exception: Exception) {
                _products.postValue(Result.Error(exception.message ?: ERROR_OCCURRED))
            }
        }
    }

    fun setRouteServiceDetails(list: List<Product>) {
        val pickedItems = list.count { it.productStatus == ProductStatus.Picked }
        val itemDetails = ItemsDetail(pickedItems, list.size)

        totalItems.value = itemDetails.totalItems
        itemsPicked.value = itemDetails.pickedItems
    }

    fun updateRouteData(routeNumber: Int, products: List<Product>) {
        viewModelScope.launch(Dispatchers.IO) {
            _routeDataUpdated.postValue(Event(Result.Fetching))
            try {
                _routeDataUpdated.postValue(
                    Event(
                        Result.Success(
                            repository.updateRouteData(
                                routeNumber,
                                products
                            )
                        )
                    )
                )
            } catch (exception: Exception) {
                _routeDataUpdated.postValue(
                    Event(
                        Result.Error(
                            exception.message ?: ERROR_OCCURRED
                        )
                    )
                )
            }
        }
    }

    fun discardChanges(routeNumber: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.discardAllChanges(routeNumber)
        }
    }

    override fun onCleared() {
        super.onCleared()
        
    }
}

data class ItemsDetail(val pickedItems: Int, val totalItems: Int)