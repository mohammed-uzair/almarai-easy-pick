package com.almarai.easypick.screens

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.almarai.data.easy_pick_models.Product
import com.almarai.data.easy_pick_models.Result
import com.almarai.data.easy_pick_models.RouteStatus
import com.almarai.data.easy_pick_models.util.exhaustive
import com.almarai.easypick.R
import com.almarai.easypick.adapters.item.ProductsAdapter
import com.almarai.easypick.utils.*
import com.almarai.easypick.view_models.ProductListViewModel
import kotlinx.android.synthetic.main.main_recycler_view.*
import kotlinx.android.synthetic.main.screen_product.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductListScreen : Fragment(R.layout.screen_product) {
    private val productListViewModel: ProductListViewModel by viewModel()
    private lateinit var navController: NavController
    private val adapter by lazy { ProductsAdapter() }
    private val args: ProductListScreenArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        init()
    }

    private fun init() {
        //Set screen title
        activity?.title = getString(R.string.title_item_list)

        setHasOptionsMenu(true)

        animateUI()
        adapter.setValues(listOf())

        productListViewModel.products.observe(viewLifecycleOwner, Observer {
            it?.let { result ->
                when (result) {
                    is Result.Fetching -> showAlert(Alert.Loading)
                    is Result.Success -> showDataUi(result.data)
                    is Result.Error -> showAlert(Alert.Error)
                }.exhaustive
            }
        })

        setRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_item_screen, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_route_action_filter -> {
                val action = ProductListScreenDirections
                    .actionProductListScreenToFilterScreen(
                        FilterScreenSource.ProductListScreen
                    )
                navController.navigate(action)
            }
            R.id.menu_item_action_save -> {
                val saveStateHandle = navController.previousBackStackEntry?.savedStateHandle

                //Set the result
                saveStateHandle?.set(
                    BundleKeys.ROUTE_PROCESSED,
                    Pair(args.SelectedRouteNumber, RouteStatus.Served)
                )

                navController.popBackStack()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showDataUi(list: List<Product>) {
        //If the received data is empty
        if (list.isEmpty()) {
            showAlert(Alert.NoDataAvailable)
            return
        }

        //Hide the alert
        hideAlert()

        adapter.setValues(list)
        adapter.notifyDataSetChanged()

        val itemServiceDetails = productListViewModel.getRouteServiceDetails(list)

        screen_product_total_items_text.text = "${itemServiceDetails.totalItems}"
        screen_product_picked_text.text = itemServiceDetails.servedItems.toString()
    }

    private fun animateUI() {
        val bottomToTop = AnimationUtils.loadAnimation(activity, R.anim.bottom_to_top)
        val topToBottom = AnimationUtils.loadAnimation(activity, R.anim.top_to_bottom)

        screen_product_background_image.startAnimation(topToBottom)

        screen_product_total_items_label.startAnimation(topToBottom)
        screen_product_total_items_text.startAnimation(topToBottom)
        screen_product_picked_text.startAnimation(topToBottom)
        screen_product_picked_label.startAnimation(topToBottom)

        screen_product_header_titles_layout.startAnimation(topToBottom)

        layout_main_recyclerview.startAnimation(bottomToTop)
    }

    private fun setRecyclerView() {
        val recyclerView = layout_main_recyclerview as RecyclerView
        recyclerView.animation = null
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }
}