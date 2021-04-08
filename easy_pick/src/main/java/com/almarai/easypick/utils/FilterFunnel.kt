package com.almarai.easypick.utils

import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.almarai.common.utils.APP_LOCALE
import com.almarai.data.easy_pick_models.Group
import com.almarai.data.easy_pick_models.filter.Filters
import com.almarai.data.easy_pick_models.product.Product
import com.almarai.data.easy_pick_models.product.ProductStatus
import com.almarai.data.easy_pick_models.route.Route
import com.almarai.data.easy_pick_models.route.RouteStatus
import com.almarai.easypick.adapters.ProductsAdapter
import com.almarai.easypick.adapters.RoutesAdapter
import java.util.*

const val FilterDifferentiation = "%"

class FilterFunnel(
    private val adapter: RecyclerView.Adapter<*>? = null,
    private val filters: Filters
) : Filterable {
    companion object {
        private const val TAG = "FilterFunnel"
    }

    private lateinit var routesToFilterFrom: List<Route>
    private lateinit var productsToFilterFrom: List<Product>

    private var filterProducts = false
    private var filterRoutes = false

    private fun filterAllRoutes(): List<Route> {
        val filteredRoutes = ArrayList<Route>()

        if (shouldFilter()) {
            for (route in routesToFilterFrom) {
                var isFilteredRoute = false

                //region Filter Logic
                if (filters.noFilter) {
                    return routesToFilterFrom
                }

                if (filters.filterBySubCategory1Bakery) {
                    if (route.group.contains(Group.Bakery)) {
                        isFilteredRoute = true
                    } else if (filters.allowMultipleFilters) {
//                        isFilteredRoute = false
                        continue
                    }
                }

                if (filters.filterBySubCategory1Dairy) {
                    if (route.group.contains(Group.Dairy)) {
                        isFilteredRoute = true
                    } else if (filters.allowMultipleFilters) {
//                        isFilteredRoute = false
                        continue
                    }
                }

                if (filters.filterBySubCategory1Poultry) {
                    if (route.group.contains(Group.Poultry)) {
                        isFilteredRoute = true
                    } else if (filters.allowMultipleFilters) {
//                        isFilteredRoute = false
                        continue
                    }
                }

                if (filters.filterBySubCategory2IPNC) {
                    if (route.group.contains(Group.IPNC)) {
                        isFilteredRoute = true
                    } else if (filters.allowMultipleFilters) {
//                        isFilteredRoute = false
                        continue
                    }
                }

                if (filters.filterBySubCategory2NonIPNC) {
                    if (route.group.contains(Group.NonIPNC)) {
                        isFilteredRoute = true
                    } else if (filters.allowMultipleFilters) {
//                        isFilteredRoute = false
                        continue
                    }
                }

                if (filters.customerOnly) {
                    if (route.group.contains(Group.Customer)) {
                        isFilteredRoute = true
                    } else if (filters.allowMultipleFilters) {
//                        isFilteredRoute = false
                        continue
                    }
                }

                //Finally
                if (isFilteredRoute) {
                    filteredRoutes.add(route)
                }
                //endregion
            }
        } else {
            filteredRoutes.addAll(routesToFilterFrom)
        }

        //region Sorting Logic
        when {
            filters.sortWithXNumber -> if (filters.sortOrderAscending) filteredRoutes.sortBy { it.number } else filteredRoutes.sortByDescending { it.number }
            filters.sortWithXDescription -> if (filters.sortOrderAscending) filteredRoutes.sortBy { it.description } else filteredRoutes.sortByDescending { it.description }
            filters.statusServed -> if (filters.sortOrderAscending) filteredRoutes.sortBy { it.routeStatus == RouteStatus.NotServed } else filteredRoutes.sortBy { it.routeStatus == RouteStatus.Served }
        }
        //endregion

        return filteredRoutes
    }

    private fun filterAllProducts(): List<Product> {
        val filteredProducts = ArrayList<Product>()

        for (product in productsToFilterFrom) {
            var isFilteredProduct = false

            //region Filter Logic
            if (filters.noFilter) {
                return productsToFilterFrom
            }

            if (filters.filterBySubCategory1Bakery) {
                if (product.group.contains(Group.Bakery)) {
                    isFilteredProduct = true
                } else if (filters.allowMultipleFilters) {
                    isFilteredProduct = false
                    continue
                }
            }

            if (filters.filterBySubCategory1Dairy) {
                if (product.group.contains(Group.Dairy)) {
                    isFilteredProduct = true
                } else if (filters.allowMultipleFilters) {
                    isFilteredProduct = false
                    continue
                }
            }

            if (filters.filterBySubCategory1Poultry) {
                if (product.group.contains(Group.Poultry)) {
                    isFilteredProduct = true
                } else if (filters.allowMultipleFilters) {
                    isFilteredProduct = false
                    continue
                }
            }

            if (filters.filterBySubCategory2IPNC) {
                if (product.group.contains(Group.IPNC)) {
                    isFilteredProduct = true
                } else if (filters.allowMultipleFilters) {
                    isFilteredProduct = false
                    continue
                }
            }

            if (filters.filterBySubCategory2NonIPNC) {
                if (product.group.contains(Group.NonIPNC)) {
                    isFilteredProduct = true
                } else if (filters.allowMultipleFilters) {
                    isFilteredProduct = false
                    continue
                }
            }

            if (filters.customerOnly) {
                if (product.group.contains(Group.Customer)) {
                    isFilteredProduct = true
                } else if (filters.allowMultipleFilters) {
                    isFilteredProduct = false
                    continue
                }
            }

            //Finally
            if (isFilteredProduct) {
                filteredProducts.add(product)
            }
            //endregion

            //region Sorting Logic
            when {
                filters.sortWithXNumber -> if (filters.sortOrderAscending) filteredProducts.sortBy { product.number } else filteredProducts.sortByDescending { product.number }
                filters.sortWithXDescription -> if (filters.sortOrderAscending) filteredProducts.sortBy { product.description } else filteredProducts.sortByDescending { product.description }
                filters.statusServed -> if (filters.sortOrderAscending) filteredProducts.sortBy { product.productStatus == ProductStatus.NotPicked } else filteredProducts.sortBy { product.productStatus == ProductStatus.Picked }
            }
            //endregion
        }

        return filteredProducts
    }

    private fun shouldFilter() =
        (filters.filterBySubCategory1Dairy ||
                filters.filterBySubCategory1Poultry ||
                filters.filterBySubCategory1Bakery ||
                filters.filterBySubCategory2IPNC ||
                filters.filterBySubCategory2NonIPNC ||
                filters.customerOnly
                )

    fun filterRoutes(routes: List<Route>) {
        this.routesToFilterFrom = routes

        filterProducts = false
        filterRoutes = true

        filter.filter(FilterDifferentiation)
    }

    fun filterProducts(products: List<Product>) {
        this.productsToFilterFrom = products

        filterProducts = true
        filterRoutes = false

        filter.filter(FilterDifferentiation)
    }

    override fun getFilter(): android.widget.Filter = object : android.widget.Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filterResults = FilterResults()

            if (constraint != null && !constraint.startsWith(FilterDifferentiation)) {
                if (filterProducts) {
                    if (constraint.isEmpty()) {
                        filterResults.values = productsToFilterFrom
                    } else {
                        filterResults.values =
                            searchAllProducts(constraint.toString())
                    }
                } else {
                    if (constraint.isEmpty()) {
                        filterResults.values = routesToFilterFrom
                    } else {
                        filterResults.values =
                            searchAllRoutes(
                                constraint.toString()
                            )
                    }
                }
            } else if (filterRoutes) filterResults.values =
                filterAllRoutes() else filterResults.values =
                filterAllProducts()

            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (adapter != null) {
                if (adapter is RoutesAdapter) {
                    //Send this filter list again to filter next constraint
                    adapter.submitList((results?.values ?: listOf<Route>()) as List<Route>)
                } else if (adapter is ProductsAdapter) {
                    //Send this filter list again to filter next constraint
                    adapter.submitList((results?.values ?: listOf<Product>()) as List<Product>)
                }

                adapter.notifyDataSetChanged()
            }
        }
    }

    fun searchAllProducts(newString: String) =
        productsToFilterFrom.filter {
            it.number.toString().contains(newString) or it.description.contains(newString)
        }

    fun searchAllRoutes(newString: String) = routesToFilterFrom.filter {
        it.number.toString().contains(newString) or it.description.toLowerCase(APP_LOCALE)
            .contains(newString.toLowerCase(APP_LOCALE))
    }

    fun searchProducts(newString: String, products: List<Product>) {
        this.productsToFilterFrom = products
        filterProducts = true
        filterRoutes = false

        filter.filter(newString)
    }

    fun searchRoutes(newString: String, routes: List<Route>) {
        this.routesToFilterFrom = routes
        filterProducts = false
        filterRoutes = true

        filter.filter(newString)
    }
}