package com.almarai.easypick.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.almarai.data.easy_pick_models.Item
import com.almarai.repository.api.ItemsRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class ItemViewModel : ViewModel(), KoinComponent {
    private val repository: ItemsRepository by inject()

    val mutableItems = MutableLiveData<List<Item>>()

    init {
        repository.getAllItems(1, "", 0, 0, mutableItems)
    }

    fun getRouteServiceDetails(list: List<Item>): ItemsDetail {
        return ItemsDetail(123, 24, list.size)
    }
}

data class ItemsDetail(val servedItems: Int, val servingItems: Int, val totalItems: Int)