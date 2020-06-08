package com.almarai.easypick.screens

import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.almarai.data.easy_pick_models.Product
import com.almarai.easypick.R
import com.almarai.easypick.adapters.item.ItemsAdapter
import com.almarai.easypick.view_models.ProductListViewModel
import kotlinx.android.synthetic.main.main_recycler_view.*
import kotlinx.android.synthetic.main.screen_product.*

class ProductListScreen : Fragment() {
    private lateinit var productListViewModel: ProductListViewModel
    private val adapter by lazy { ItemsAdapter() }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        productListViewModel = ViewModelProvider(this).get(ProductListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.screen_product, container, false)
    }

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

        productListViewModel.mutableItems.observe(viewLifecycleOwner, Observer { list ->
            //If the list is empty
            if (list.isNotEmpty()) showDataUi(list) else showNoDataUi()
        })

        setRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_item_screen, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_action_filter -> navController.navigate(R.id.action_productListScreen_to_filterScreen)
            R.id.menu_item_action_save -> navController.popBackStack()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showDataUi(list: List<Product>) {
//        no_event_found_layout.visibility = View.GONE
//        device_events_layout.visibility = View.VISIBLE

        adapter.setValues(list)
        adapter.notifyDataSetChanged()

        val itemServiceDetails = productListViewModel.getRouteServiceDetails(list)

        screen_product_total_items_text.text = "${itemServiceDetails.totalItems}"
        screen_product_picked_text.text = itemServiceDetails.servedItems.toString()
    }

    private fun showNoDataUi() {
//        no_event_found_layout.visibility = View.VISIBLE
//        device_events_layout.visibility = View.GONE
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