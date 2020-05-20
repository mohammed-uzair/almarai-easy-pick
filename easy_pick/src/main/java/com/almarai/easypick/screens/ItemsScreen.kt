package com.almarai.easypick.screens

import android.content.res.Resources
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.almarai.data.easy_pick_models.Item
import com.almarai.easypick.MainActivity
import com.almarai.easypick.R
import com.almarai.easypick.adapters.item.ItemsAdapter
import com.almarai.easypick.view_models.ItemViewModel
import kotlinx.android.synthetic.main.main_recycler_view.*
import kotlinx.android.synthetic.main.screen_items.*


class ItemsScreen : Fragment() {
    //     Lazy Inject ViewModel
    private lateinit var itemViewModel: ItemViewModel
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

        itemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)

        init()
    }

    private fun init() {
        //Set screen title
        activity?.title = getString(R.string.title_item_list)

        setHasOptionsMenu(true)

        animateUI()
        setToolbarTheme()
        adapter.setValues(listOf())

        itemViewModel.mutableItems.observe(viewLifecycleOwner, Observer { list ->
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
            R.id.menu_item_action_save -> navController.popBackStack()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showDataUi(list: List<Item>) {
//        no_event_found_layout.visibility = View.GONE
//        device_events_layout.visibility = View.VISIBLE

        adapter.setValues(list)
        adapter.notifyDataSetChanged()

        val itemServiceDetails = itemViewModel.getRouteServiceDetails(list)

        screen_items_total_items_text.text = "${itemServiceDetails.totalItems}"
        screen_items_picked_text.text = itemServiceDetails.servedItems.toString()
    }

    private fun showNoDataUi() {
//        no_event_found_layout.visibility = View.VISIBLE
//        device_events_layout.visibility = View.GONE
    }

    private fun animateUI() {

    }

    private fun setToolbarTheme() {
        // Get the primary text color of the theme
        val typedValue = TypedValue()
        val theme: Resources.Theme = requireActivity().theme
        theme.resolveAttribute(android.R.attr.textColorPrimary, typedValue, true)
        val arr = requireActivity().obtainStyledAttributes(
            typedValue.data, intArrayOf(
                android.R.attr.colorPrimary
            )
        )
        val primaryColor = arr.getColor(0, -1)


        (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(
            ColorDrawable(primaryColor)
        )

        val toolbar: Toolbar =
            (activity as MainActivity).findViewById(R.id.fragment_container_toolbar_included_layout)

        toolbar.setTitleTextColor(resources.getColor(android.R.color.white, theme))

        val upArrow = resources.getDrawable(R.drawable.abc_ic_ab_back_material, theme)
        upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        (activity as MainActivity).supportActionBar?.setHomeAsUpIndicator(upArrow)

        arr.recycle()

        requireActivity().actionBar?.themedContext?.setTheme(R.style.Theme_Base_ToolbarThemeWhite)
    }

    private fun setRecyclerView() {
        val recyclerView = layout_main_recyclerview as RecyclerView
        recyclerView.animation = null
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }
}
