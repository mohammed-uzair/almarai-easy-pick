package com.almarai.easypick.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.almarai.business.CratesAndPieces
import com.almarai.data.easy_pick_models.Result
import com.almarai.data.easy_pick_models.filter.Filters
import com.almarai.data.easy_pick_models.product.Product
import com.almarai.data.easy_pick_models.product.ProductStatus
import com.almarai.data.easy_pick_models.route.RouteStatus
import com.almarai.data.easy_pick_models.util.ERROR_OCCURRED
import com.almarai.easypick.utils.Event
import com.almarai.repository.api.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(private val repository: ProductsRepository) :
    ViewModel() {
    private val _products = MutableLiveData<Result<List<Product>>>()
    internal val products: LiveData<Result<List<Product>>> = _products

    private val _routeDataUpdated = MutableLiveData<Event<Result<RouteStatus>>>()
    internal val routeDataUpdatedCategory: LiveData<Event<Result<RouteStatus>>> = _routeDataUpdated

    private val _productUpdated = MutableStateFlow<UpdateProduct?>(null)
    internal val productUpdated: StateFlow<UpdateProduct?> = _productUpdated

    private val _highlightItem = MutableStateFlow<HighlightItem?>(null)
    internal val highlightItem: StateFlow<HighlightItem?> = _highlightItem

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

    fun updateProduct(updateProduct: UpdateProduct?) {
        _productUpdated.value = updateProduct
    }

    fun highlightRecentItemInList(highlightItem: HighlightItem) {
        _highlightItem.value = highlightItem
    }
}

data class UpdateProduct(
    val position: Int,
    val product: Product,
    val cratesAndPieces: CratesAndPieces,
    val dialogHeight: Int
)

data class HighlightItem(
    val indexPos: Int,
    val dialogHeight: Int
)

data class ItemsDetail(
    val pickedItems: Int,
    val totalItems: Int
)