package com.almarai.easypick.view_models

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.almarai.data.easy_pick_models.filter.Filter
import com.almarai.repository.api.ApplicationRepository

class FilterViewModel(private val applicationRepository: ApplicationRepository) : ViewModel(),
    LifecycleObserver {
    companion object {
        const val TAG = "FilterViewModel"
    }

    //region Exposed variable
    val noFilter = MutableLiveData(true)
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
    val filterBySubCategory2Tc = MutableLiveData(false)
    val filterBySubCategory2NonTc = MutableLiveData(false)
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
        filterBySubCategory2NonIPNC,
        filterBySubCategory2Tc,
        filterBySubCategory2NonTc
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
        filterBySubCategory2Tc,
        filterBySubCategory2NonTc,
        customerOnly,
        allowMultipleFilters
    )
    //endregion

    fun unCheckAllFilters() { _allFiltersList.filter { it.value == true }.forEach { it.value = false } }
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

    fun getFilterModel() = Filter(
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
        filterBySubCategory2Tc.value!!,
        filterBySubCategory2NonTc.value!!,
        customerOnly.value!!,
        allowMultipleFilters.value!!,
        persistFilters.value!!
    )

    fun setFilterModel(filter: Filter?) {
        noFilter.value = filter?.noFilter
        sortOrderAscending.value = filter?.sortOrderAscending
        sortOrderDescending.value = filter?.sortOrderDescending
        sortWithXNumber.value = filter?.sortWithXNumber
        sortWithXDescription.value = filter?.sortWithXDescription
        statusServed.value = filter?.statusServed
        filterByAllSubCategory1.value = filter?.filterByAllSubCategory1
        filterBySubCategory1Dairy.value = filter?.filterBySubCategory1Dairy
        filterBySubCategory1Poultry.value = filter?.filterBySubCategory1Poultry
        filterBySubCategory1Bakery.value = filter?.filterBySubCategory1Bakery
        filterByAllSubCategory2.value = filter?.filterByAllSubCategory2
        filterBySubCategory2IPNC.value = filter?.filterBySubCategory2IPNC
        filterBySubCategory2NonIPNC.value = filter?.filterBySubCategory2NonIPNC
        filterBySubCategory2Tc.value = filter?.filterBySubCategory2Tc
        filterBySubCategory2NonTc.value = filter?.filterBySubCategory2NonTc
        customerOnly.value = filter?.customerOnly
        allowMultipleFilters.value = filter?.allowMultipleFilters
        persistFilters.value = filter?.persistFilters
    }
}