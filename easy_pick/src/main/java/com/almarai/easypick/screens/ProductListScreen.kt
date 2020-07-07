package com.almarai.easypick.screens

import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.almarai.data.easy_pick_models.Product
import com.almarai.data.easy_pick_models.Result
import com.almarai.data.easy_pick_models.RouteStatus
import com.almarai.data.easy_pick_models.filter.Filter
import com.almarai.data.easy_pick_models.util.exhaustive
import com.almarai.easypick.R
import com.almarai.easypick.adapters.item.ProductsAdapter
import com.almarai.easypick.databinding.ScreenProductBinding
import com.almarai.easypick.extensions.Alert
import com.almarai.easypick.extensions.hideViewStateAlert
import com.almarai.easypick.extensions.showViewStateAlert
import com.almarai.easypick.utils.BundleKeys
import com.almarai.easypick.utils.FilterScreenSource
import com.almarai.easypick.view_models.ProductListViewModel
import com.google.android.material.transition.MaterialContainerTransform
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductListScreen : Fragment() {
    private val productListViewModel: ProductListViewModel by viewModel()
    private lateinit var navController: NavController
    private val adapter by lazy { ProductsAdapter() }
    private val args: ProductListScreenArgs by navArgs()
    private lateinit var screenProductBinding: ScreenProductBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        screenProductBinding =
            DataBindingUtil.inflate(inflater, R.layout.screen_product, container, false)
        screenProductBinding.apply {
            lifecycleOwner = this@ProductListScreen
            viewModel = this@ProductListScreen.productListViewModel
        }

        return screenProductBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val transformation = MaterialContainerTransform().apply {
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
            duration = 500
        }
        sharedElementEnterTransition = transformation
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

        productListViewModel.products.observe(viewLifecycleOwner, Observer {
            it?.let { result ->
                when (result) {
                    is Result.Fetching -> showViewStateAlert(Alert.Loading)
                    is Result.Success -> showDataUi(result.data)
                    is Result.Error -> showViewStateAlert(Alert.Error)
                }.exhaustive
            }
        })

        setRecyclerView()

        val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle

        savedStateHandle?.getLiveData<Filter>(BundleKeys.FILTER_MODEL)?.observe(
            viewLifecycleOwner,
            Observer { result ->
                productListViewModel.filterModel = result

                //Filter the list


                //Notify the adapter
                adapter.notifyDataSetChanged()
            })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_item_screen, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_route_action_filter -> {
                val action = ProductListScreenDirections
                    .actionProductListScreenToFilterScreen(
                        FilterScreenSource.ProductListScreen,
                        productListViewModel.filterModel
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
            showViewStateAlert(Alert.NoDataAvailable)
            return
        }

        //Hide the alert
        hideViewStateAlert()

        adapter.setValues(list)
        adapter.notifyDataSetChanged()

        productListViewModel.setRouteServiceDetails(list)
    }

    private fun animateUI() {
        val bottomToTop = AnimationUtils.loadAnimation(activity, R.anim.bottom_to_top)
        val topToBottom = AnimationUtils.loadAnimation(activity, R.anim.top_to_bottom)

        screenProductBinding.screenProductBackgroundImage.startAnimation(topToBottom)

        screenProductBinding.screenProductTotalItemsLabel.startAnimation(topToBottom)
        screenProductBinding.screenProductTotalItemsText.startAnimation(topToBottom)
        screenProductBinding.screenProductPickedLabel.startAnimation(topToBottom)
        screenProductBinding.screenProductPickedText.startAnimation(topToBottom)

//        screen_product_header_titles_layout.startAnimation(topToBottom)

        screenProductBinding.recyclerView.layoutMainRecyclerview.startAnimation(bottomToTop)
    }

    private fun setRecyclerView() {
        val recyclerView = screenProductBinding.recyclerView.layoutMainRecyclerview
        recyclerView.animation = null
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }
}