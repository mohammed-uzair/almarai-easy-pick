package com.almarai.easypick.utils

import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.almarai.data.easy_pick_models.Product
import com.almarai.data.easy_pick_models.Route
import com.almarai.data.easy_pick_models.filter.Filter
import java.util.*

class FilterFunnel(
    private val adapter: RecyclerView.Adapter<*>? = null,
    private val filters: Filter
)  {
//    private lateinit var listToFilter

    fun filterRoutes(listToFilter: List<Route>) {
//        this.listToFilter = listToFilter
//        filter.filter("")

//        if (!filters.allowMultipleFilters) {
//            listToFilter.asSequence()
//                .filter {
//                }
//        }
    }

    fun filterProducts(listToFilter: List<Product>) {
//        filter.filter("")
    }

    private fun filterNoFilter() {

    }

    private fun filterTc() {

    }

//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(constraint: CharSequence): FilterResults {
//                val filteredList: MutableList<SalesProductDetailModel> =
//                    ArrayList<SalesProductDetailModel>()
//
//                for (salesProductDetailModel in listToFilter!!) {
//                    var isFilteredProduct = false
//
//                    if (filters.noFilter) {
//                        filteredList.addAll(listToFilter!!)
//                        break
//                    }
//                    if (filters.filterBySubCategory2Tc) {
//                        if (salesProductDetailModel.AllowTC === CommonParams.ENABLED) {
//                            isFilteredProduct = true
//                        } else if (filters.allowMultipleFilters) {
//                            isFilteredProduct = false
//                            continue
//                        }
//                    }
//                    if (filters.filterBySubCategory2NonTc) {
//                        if (salesProductDetailModel.AllowTC === CommonParams.DISABLED) {
//                            isFilteredProduct = true
//                        } else if (filters.allowMultipleFilters) {
//                            isFilteredProduct = false
//                            continue
//                        }
//                    }
//                    if (filters.shouldFilterIpncProducts) {
//                        if (salesProductDetailModel.BatchEntryEnabled === CommonParams.ENABLED) {
//                            isFilteredProduct = true
//                        } else if (filters.allowMultipleFilters) {
//                            isFilteredProduct = false
//                            continue
//                        }
//                    }
//                    if (filters.shouldFilterNonIpncProducts) {
//                        if (salesProductDetailModel.BatchEntryEnabled === CommonParams.DISABLED) {
//                            isFilteredProduct = true
//                        } else if (filters.allowMultipleFilters) {
//                            isFilteredProduct = false
//                            continue
//                        }
//                    }
//                    if (filters.shouldFilterDairyProducts) {
//                        if (salesProductDetailModel.GroupNumber === DBITMParam.GroupNumber.DAIRY) {
//                            isFilteredProduct = true
//                        } else if (filters.allowMultipleFilters) {
//                            isFilteredProduct = false
//                            continue
//                        }
//                    }
//                    if (filters.shouldFilterBakeryProducts) {
//                        if (salesProductDetailModel.GroupNumber === DBITMParam.GroupNumber.BAKERY) {
//                            isFilteredProduct = true
//                        } else if (filters.allowMultipleFilters) {
//                            isFilteredProduct = false
//                            continue
//                        }
//                    }
//                    if (filters.shouldFilterPoultryProducts) {
//                        if (salesProductDetailModel.GroupNumber === DBITMParam.GroupNumber.POULTRY) {
//                            isFilteredProduct = true
//                        } else if (filters.allowMultipleFilters) {
//                            isFilteredProduct = false
//                            continue
//                        }
//                    }
//                    if (filters.shouldFilterDistributionExceptionProducts) if (salesProductDetailModel.ExceptionIndicator === CommonParams.ENABLED) {
//                        isFilteredProduct = true
//                    } else if (filters.allowMultipleFilters) {
//                        isFilteredProduct = false
//                        continue
//                    }
//                    if (isFilteredProduct) {
//                        filteredList.add(salesProductDetailModel)
//                    }
//                }
//                val filterResults = FilterResults()
//                filterResults.values = filteredList
//                return filterResults
//            }
//
//            override fun publishResults(
//                constraint: CharSequence,
//                results: FilterResults
//            ) {
//                listToFilter = null
//                listToFilter =
//                    results.values as List<SalesProductDetailModel>
//                if (adapter != null) {
//                    if (adapter is SalesAdapterDefault) {
//                        //Send this filter list again to filter next constraint
//                        val currentAdapter: SalesAdapterDefault =
//                            adapter as SalesAdapterDefault
//                        currentAdapter.setList(listToFilter)
//                    } else if (adapter is SalesAdapterAsrm) {
//                        //Send this filter list again to filter next constraint
//                        val currentAdapter: SalesAdapterAsrm = adapter as SalesAdapterAsrm
//                        currentAdapter.setList(listToFilter)
//                    } else if (adapter is SalesAdapterPoq) {
//                        //Send this filter list again to filter next constraint
//                        val currentAdapter: SalesAdapterPoq = adapter as SalesAdapterPoq
//                        currentAdapter.setList(listToFilter)
//                    }
//                    adapter!!.notifyDataSetChanged()
//                }
//            }
//        }
//    }

    companion object {
        private const val TAG = "FilterFunnel"
    }
}