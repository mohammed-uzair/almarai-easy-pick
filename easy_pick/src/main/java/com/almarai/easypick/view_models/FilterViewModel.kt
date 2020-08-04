package com.almarai.easypick.view_models

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.almarai.data.easy_pick_models.filter.Filters
import com.almarai.repository.api.ApplicationRepository

class FilterViewModel @ViewModelInject constructor(private val applicationRepository: ApplicationRepository) : ViewModel(),
    LifecycleObserver {
    companion object {
        const val TAG = "FilterViewModel"
    }

    //region Exposed variable
    var forceChanged = false
    val noFilter = MutableLiveData(false)
    val sortOrderAscending = MutableLiveData(false)
    val sortOrderDescending = MutableLiveData(false)
    val sortWithXNumber = MutableLiveData(false)
    val sortWithXDescription = MutableLiveData(false)
    val statusServed = MutableLiveData(false)
    val filterByAllSubCategory1 = MutableLiveData(false)
    val filterBySubCategory1Dairy = MutableLiveData(false)
    val filterBySubCategory1Poultry = MutableLiveData(false)
    val filterBySubCategory1Bakery = MutableLiveData(false)
    val filterByAllSubCategory2 = MutableLiveData(false)
    val filterBySubCategory2IPNC = MutableLiveData(false)
    val filterBySubCategory2NonIPNC = MutableLiveData(false)
    val customerOnly = MutableLiveData(false)
    val allowMultipleFilters = MutableLiveData(false)
    val persistFilters = MutableLiveData(false)
    //endregion

    //region Non exposed variables
    //Lists
    private val _filterBySubCategory1List =
        listOf(filterBySubCategory1Dairy, filterBySubCategory1Poultry, filterBySubCategory1Bakery)
    private val _filterBySubCategory2List = listOf(
        filterBySubCategory2IPNC,
        filterBySubCategory2NonIPNC
    )
    private val _allFiltersList = listOf(
        sortOrderAscending,
        sortOrderDescending,
        sortWithXNumber,
        sortWithXDescription,
        statusServed,
        filterByAllSubCategory1,
        filterBySubCategory1Dairy,
        filterBySubCategory1Poultry,
        filterBySubCategory1Bakery,
        filterByAllSubCategory2,
        filterBySubCategory2IPNC,
        filterBySubCategory2NonIPNC,
        customerOnly,
        allowMultipleFilters
    )
    //endregion

    fun unCheckAllFilters() {
        _allFiltersList.filter { it.value == true }.forEach { it.value = false }
    }

    fun toggleSubCategory1(value: Boolean) = _filterBySubCategory1List.forEach { it.value = value }
    fun toggleSubCategory2(value: Boolean) = _filterBySubCategory2List.forEach { it.value = value }

    fun checkNoFilterState() {
        if (noFilter.value == false) {
            try {
                Log.d(TAG, "checkNoFilterState: Checking if true")
                _allFiltersList.first { it.value == true }
            } catch (noElementFound: NoSuchElementException) {
                noFilter.value = true
                Log.d(TAG, "checkNoFilterState: Setting no filter to false")
            }
        }
    }

    fun unCheckNoFilter() {
        Log.d(TAG, "unCheckNoFilter")
        if (noFilter.value == true) {
            noFilter.value = false
            Log.d(TAG, "unCheckNoFilter: No filter is set to False")
        }
    }

    fun persistFilters() {
        //Add all the views to the data source
        //Create a model
        val filter = getFilterModel()

//        applicationRepository.setFilter()
    }

    fun checkSortWith() {
        if (sortWithXNumber.value == false &&
            sortWithXDescription.value == false &&
            statusServed.value == false
        ) {
            sortWithXNumber.value = true
        }
    }

    fun checkSortIn() {
        if (sortOrderAscending.value == false &&
            sortOrderDescending.value == false
        ) {
            sortOrderAscending.value = true
        }
    }

//    fun checkSubCategory1Switch() {
//        val result = _filterBySubCategory1List.all { it.value == true }
//        if(result && filterByAllSubCategory1.value != result) {
//            filterByAllSubCategory1.value = result
//        }
//        else if(!result && filterByAllSubCategory1.value == true){
//            filterByAllSubCategory1.value = false
//        }
//    }
//
//    fun checkSubCategory2Switch() {
//        val result = _filterBySubCategory2List.all { it.value == true }
//        if(result && filterByAllSubCategory2.value != result) {
//            filterByAllSubCategory2.value = result
//        }
//        else if(!result && filterByAllSubCategory2.value == true){
//            filterByAllSubCategory2.value = false
//        }
//    }

    fun getFilterModel() = Filters(
        noFilter.value!!,
        sortOrderAscending.value!!,
        sortOrderDescending.value!!,
        sortWithXNumber.value!!,
        sortWithXDescription.value!!,
        statusServed.value!!,
        filterByAllSubCategory1.value!!,
        filterBySubCategory1Dairy.value!!,
        filterBySubCategory1Poultry.value!!,
        filterBySubCategory1Bakery.value!!,
        filterByAllSubCategory2.value!!,
        filterBySubCategory2IPNC.value!!,
        filterBySubCategory2NonIPNC.value!!,
        customerOnly.value!!,
        allowMultipleFilters.value!!,
        persistFilters.value!!
    )

    fun setFilterModel(filters: Filters?) {
        noFilter.value = filters?.noFilter
        sortOrderAscending.value = filters?.sortOrderAscending
        sortOrderDescending.value = filters?.sortOrderDescending
        sortWithXNumber.value = filters?.sortWithXNumber
        sortWithXDescription.value = filters?.sortWithXDescription
        statusServed.value = filters?.statusServed
        filterByAllSubCategory1.value = filters?.filterByAllSubCategory1
        filterBySubCategory1Dairy.value = filters?.filterBySubCategory1Dairy
        filterBySubCategory1Poultry.value = filters?.filterBySubCategory1Poultry
        filterBySubCategory1Bakery.value = filters?.filterBySubCategory1Bakery
        filterByAllSubCategory2.value = filters?.filterByAllSubCategory2
        filterBySubCategory2IPNC.value = filters?.filterBySubCategory2IPNC
        filterBySubCategory2NonIPNC.value = filters?.filterBySubCategory2NonIPNC
        customerOnly.value = filters?.customerOnly
        allowMultipleFilters.value = filters?.allowMultipleFilters
        persistFilters.value = filters?.persistFilters
    }
}