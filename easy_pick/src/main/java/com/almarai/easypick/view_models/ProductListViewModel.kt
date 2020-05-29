package com.almarai.easypick.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.almarai.data.easy_pick_models.Product
import com.almarai.repository.api.ProductsRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class ProductListViewModel : ViewModel(), KoinComponent {
    private val repository: ProductsRepository by inject()

    val mutableItems = MutableLiveData<List<Product>>()

    init {
        repository.getAllProducts(1, "", 0, 0, mutableItems)
    }

    fun getRouteServiceDetails(list: List<Product>): ItemsDetail {
        return ItemsDetail(123, 24, list.size)
    }
}

data class ItemsDetail(val servedItems: Int, val servingItems: Int, val totalItems: Int)