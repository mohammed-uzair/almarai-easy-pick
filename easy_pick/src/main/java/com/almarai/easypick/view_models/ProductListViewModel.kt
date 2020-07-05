package com.almarai.easypick.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.almarai.data.easy_pick_models.Product
import com.almarai.data.easy_pick_models.Result
import com.almarai.data.easy_pick_models.filter.Filter
import com.almarai.data.easy_pick_models.util.ERROR_OCCURRED
import com.almarai.repository.api.ProductsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductListViewModel(private val repository: ProductsRepository) : ViewModel() {
    private val _products = MutableLiveData<Result<List<Product>>>()
    internal var filterModel: Filter? = null
    internal val products: LiveData<Result<List<Product>>> = _products
    val totalItems: MutableLiveData<Int> = MutableLiveData(0)
    val itemsPicked: MutableLiveData<Int> = MutableLiveData(0)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _products.postValue(Result.Fetching)
            try {
                _products.postValue(Result.Success(repository.getAllProducts(1, "", 0)))
            } catch (exception: Exception) {
                _products.postValue(Result.Error(exception.message ?: ERROR_OCCURRED))
            }
        }
    }

    fun setRouteServiceDetails(list: List<Product>) {
        val itemDetails = ItemsDetail(23, list.size)

        totalItems.value = itemDetails.totalItems
        itemsPicked.value = itemDetails.pickedItems
    }
}

data class ItemsDetail(val pickedItems: Int, val totalItems: Int)