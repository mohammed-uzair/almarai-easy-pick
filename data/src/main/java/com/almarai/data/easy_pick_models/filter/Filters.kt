package com.almarai.data.easy_pick_models.filter

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Filters(
    val noFilter: Boolean = false,
    val sortOrderAscending: Boolean = false,
    val sortOrderDescending: Boolean = false,
    val sortWithXNumber: Boolean = false,
    val sortWithXDescription: Boolean = false,
    val statusServed: Boolean = false,
    val filterByAllSubCategory1: Boolean = false,
    val filterBySubCategory1Dairy: Boolean = false,
    val filterBySubCategory1Poultry: Boolean = false,
    val filterBySubCategory1Bakery: Boolean = false,
    val filterByAllSubCategory2: Boolean = false,
    val filterBySubCategory2IPNC: Boolean = false,
    val filterBySubCategory2NonIPNC: Boolean = false,
    val customerOnly: Boolean = false,
    val allowMultipleFilters: Boolean = false,
    val persistFilters: Boolean = false
) : Parcelable