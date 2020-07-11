package com.almarai.easypick.utils

import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.almarai.data.easy_pick_models.*
import com.almarai.data.easy_pick_models.filter.Filters
import com.almarai.easypick.adapters.item.ProductsAdapter
import com.almarai.easypick.adapters.route.RoutesAdapter
import java.util.*

class FilterFunnel(
    private val adapter: RecyclerView.Adapter<*>? = null,
    private val filters: Filters
) : Filterable {
    companion object {
        private const val TAG = "FilterFunnel"
    }

    private lateinit var routes: List<Route>
    private lateinit var products: List<Product>

    private var filterProducts = false
    private var filterRoutes = false

    private fun filterAllRoutes(): List<Route> {
        val filteredRoutes = ArrayList<Route>()

        if (shouldFilter()) {
            for (route in routes) {
                var isFilteredRoute = false

                //region Filter Logic
                if (filters.noFilter) {
                    return routes
                }

                if (filters.filterBySubCategory1Bakery) {
                    if (route.group.contains(GroupType.Bakery)) {
                        isFilteredRoute = true
                    } else if (filters.allowMultipleFilters) {
//                        isFilteredRoute = false
                        continue
                    }
                }

                if (filters.filterBySubCategory1Dairy) {
                    if (route.group.contains(GroupType.Dairy)) {
                        isFilteredRoute = true
                    } else if (filters.allowMultipleFilters) {
//                        isFilteredRoute = false
                        continue
                    }
                }

                if (filters.filterBySubCategory1Poultry) {
                    if (route.group.contains(GroupType.Poultry)) {
                        isFilteredRoute = true
                    } else if (filters.allowMultipleFilters) {
//                        isFilteredRoute = false
                        continue
                    }
                }

                if (filters.filterBySubCategory2IPNC) {
                    if (route.group.contains(GroupType.IPNC)) {
                        isFilteredRoute = true
                    } else if (filters.allowMultipleFilters) {
//                        isFilteredRoute = false
                        continue
                    }
                }

                if (filters.filterBySubCategory2NonIPNC) {
                    if (route.group.contains(GroupType.NonIPNC)) {
                        isFilteredRoute = true
                    } else if (filters.allowMultipleFilters) {
//                        isFilteredRoute = false
                        continue
                    }
                }

                if (filters.customerOnly) {
                    if (route.group.contains(GroupType.Customer)) {
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
            filteredRoutes.addAll(routes)
        }

        //region Sorting Logic
        when {
            filters.sortWithXNumber -> if (filters.sortOrderAscending) filteredRoutes.sortBy { it.number } else filteredRoutes.sortByDescending { it.number }
            filters.sortWithXDescription -> if (filters.sortOrderAscending) filteredRoutes.sortBy { it.description } else filteredRoutes.sortByDescending { it.description }
            filters.statusServed -> if (filters.sortOrderAscending) filteredRoutes.sortBy { it.serviceStatus == RouteServiceStatus.NotServed } else filteredRoutes.sortBy { it.serviceStatus == RouteServiceStatus.Served }
        }
        //endregion

        return filteredRoutes
    }

    private fun filterAllProducts(): List<Product> {
        val filteredProducts = ArrayList<Product>()

        for (product in products) {
            var isFilteredProduct = false

            //region Filter Logic
            if (filters.noFilter) {
                return products
            }

            if (filters.filterBySubCategory1Bakery) {
                if (product.group.contains(GroupType.Bakery)) {
                    isFilteredProduct = true
                } else if (filters.allowMultipleFilters) {
                    isFilteredProduct = false
                    continue
                }
            }

            if (filters.filterBySubCategory1Dairy) {
                if (product.group.contains(GroupType.Dairy)) {
                    isFilteredProduct = true
                } else if (filters.allowMultipleFilters) {
                    isFilteredProduct = false
                    continue
                }
            }

            if (filters.filterBySubCategory1Poultry) {
                if (product.group.contains(GroupType.Poultry)) {
                    isFilteredProduct = true
                } else if (filters.allowMultipleFilters) {
                    isFilteredProduct = false
                    continue
                }
            }

            if (filters.filterBySubCategory2IPNC) {
                if (product.group.contains(GroupType.IPNC)) {
                    isFilteredProduct = true
                } else if (filters.allowMultipleFilters) {
                    isFilteredProduct = false
                    continue
                }
            }

            if (filters.filterBySubCategory2NonIPNC) {
                if (product.group.contains(GroupType.NonIPNC)) {
                    isFilteredProduct = true
                } else if (filters.allowMultipleFilters) {
                    isFilteredProduct = false
                    continue
                }
            }

            if (filters.customerOnly) {
                if (product.group.contains(GroupType.Customer)) {
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
                filters.statusServed -> if (filters.sortOrderAscending) filteredProducts.sortBy { product.status == ProductStatus.NotPicked } else filteredProducts.sortBy { product.status == ProductStatus.Picked }
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
        this.routes = routes

        filterProducts = false
        filterRoutes = true

        filter.filter("")
    }

    fun filterProducts(products: List<Product>) {
        this.products = products

        filterProducts = true
        filterRoutes = false

        filter.filter("")
    }

    override fun getFilter(): android.widget.Filter = object : android.widget.Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filterResults = FilterResults()

            if (filterRoutes) filterResults.values = filterAllRoutes() else filterResults.values =
                filterAllProducts()

            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (adapter != null) {
                if (adapter is RoutesAdapter) {
                    //Send this filter list again to filter next constraint
                    adapter.routes = (results?.values ?: listOf<Route>()) as List<Route>
                } else if (adapter is ProductsAdapter) {
                    //Send this filter list again to filter next constraint
                    adapter.products = (results?.values ?: listOf<Product>()) as List<Product>
                }

                adapter.notifyDataSetChanged()
            }
        }
    }
}