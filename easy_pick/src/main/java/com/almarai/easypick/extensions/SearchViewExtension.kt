package com.almarai.easypick.extensions

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.annotation.StringRes
import androidx.appcompat.widget.SearchView
import com.almarai.easypick.R


internal fun SearchView.setSearchView(
    queryTextListener: SearchView.OnQueryTextListener,
    @StringRes hint: Int = 0, searchInputType: Int
) {
    //Set the Query Change Listener
    setOnQueryTextListener(queryTextListener)

    //Set the hint for the search query
    queryHint = context.getString(R.string.hint_search_product)

    //This only has a numeric input type
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
}