package com.almarai.easypick.extensions

import android.view.View
import android.widget.LinearLayout
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.almarai.easypick.R

internal fun SearchView.setSearchView(
    queryTextListener: SearchView.OnQueryTextListener,
    @StringRes hint: Int = 0, searchInputType: Int
) {
    //Set the Query Change Listener
    setOnQueryTextListener(queryTextListener)

    //Set the hint for the search query
    queryHint = context.getString(hint)

    //Set the input type
    inputType = searchInputType

    //Make the search view to be viewed as icon until user clicks on it
    setIconifiedByDefault(true)

    //Get the SearchView to disable the keyboard on focus
    val ll = getChildAt(0) as LinearLayout
    val ll2 = ll.getChildAt(2) as LinearLayout
    val ll3 = ll2.getChildAt(1) as LinearLayout
    val autoComplete = ll3.getChildAt(0) as SearchView.SearchAutoComplete
    autoComplete.setSelectAllOnFocus(true)
    autoComplete.showSoftInputOnFocus = false
    autoComplete.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_barcode, 0)
}

internal fun Fragment.setHasSearchView(toggle: Boolean) {
//    val searchView =
//        (requireActivity() as AppCompatActivity).supportActionBar?.customView
//            ?.findViewById<com.almarai.easypick.common.custom_views.search_view.SearchView>(
//                R.id.main_search_view
//            )
//
//    searchView?.visibility = if (toggle) View.VISIBLE else View.GONE
}