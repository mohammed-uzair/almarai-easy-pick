package com.almarai.easypick.screens

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.almarai.data.easy_pick_models.Product
import com.almarai.easypick.MainActivity
import com.almarai.easypick.R
import com.almarai.easypick.adapters.item.ItemsAdapter
import com.almarai.easypick.view_models.ProductListViewModel
import kotlinx.android.synthetic.main.main_recycler_view.*
import kotlinx.android.synthetic.main.screen_items.*

class ProductListScreen : Fragment() {
    //     Lazy Inject ViewModel
    private lateinit var productListViewModel: ProductListViewModel
    private val adapter by lazy { ItemsAdapter() }

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.screen_items, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        productListViewModel = ViewModelProvider(this).get(ProductListViewModel::class.java)

        init()
    }

    private fun init() {
        //Set screen title
        activity?.title = getString(R.string.title_item_list)

        setHasOptionsMenu(true)

        animateUI()
        setToolbarTheme()
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

        screen_items_total_items_text.text = "${itemServiceDetails.totalItems}"
        screen_items_picked_text.text = itemServiceDetails.servedItems.toString()
    }

    private fun showNoDataUi() {
//        no_event_found_layout.visibility = View.VISIBLE
//        device_events_layout.visibility = View.GONE
    }

    private fun animateUI() {

    }

    @SuppressLint("PrivateResource")
    private fun setToolbarTheme() {
//        // Get the primary text color of the theme
//        val typedValue = TypedValue()
//        val theme: Resources.Theme = requireActivity().theme
//        theme.resolveAttribute(android.R.attr.textColorPrimary, typedValue, true)
//        val arr = requireActivity().obtainStyledAttributes(
//            typedValue.data, intArrayOf(
//                android.R.attr.colorPrimary
//            )
//        )
//        val primaryColor = arr.getColor(0, -1)
//
//
//        (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(
//            ColorDrawable(primaryColor)
//        )
//
//        val toolbar: Toolbar =
//            (activity as MainActivity).findViewById(R.id.toolbar)
//
//        toolbar.setTitleTextColor(resources.getColor(android.R.color.white, theme))
//
//        val upArrow = resources.getDrawable(R.drawable.abc_ic_ab_back_material, theme)
//        upArrow.colorFilter =
//            BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
//                Color.parseColor("#FFFFFF"),
//                BlendModeCompat.SRC_ATOP
//            )
//        (activity as MainActivity).supportActionBar?.setHomeAsUpIndicator(upArrow)
//
//        arr.recycle()
//
//        requireActivity().actionBar?.themedContext?.setTheme(R.style.Theme_Base_ToolbarTheme)
    }

    private fun setRecyclerView() {
        val recyclerView = layout_main_recyclerview as RecyclerView
        recyclerView.animation = null
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }
}