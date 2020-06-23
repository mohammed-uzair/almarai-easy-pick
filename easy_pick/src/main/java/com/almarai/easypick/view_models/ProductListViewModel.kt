package com.almarai.easypick.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.almarai.data.easy_pick_models.Product
import com.almarai.data.easy_pick_models.Result
import com.almarai.data.easy_pick_models.util.ERROR_OCCURRED
import com.almarai.repository.api.ProductsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductListViewModel(private val repository: ProductsRepository) : ViewModel() {
    private val _products = MutableLiveData<Result<List<Product>>>()
    val products: LiveData<Result<List<Product>>> = _products

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

    fun getRouteServiceDetails(list: List<Product>): ItemsDetail {
        return ItemsDetail(123, 24, list.size)
    }
}

data class ItemsDetail(val servedItems: Int, val servingItems: Int, val totalItems: Int)