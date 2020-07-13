package com.almarai.easypick.screens

import android.app.Activity
import android.os.Bundle
import android.text.InputType
import android.view.*
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.almarai.data.easy_pick_models.Product
import com.almarai.data.easy_pick_models.Result
import com.almarai.data.easy_pick_models.RouteStatus
import com.almarai.data.easy_pick_models.filter.Filters
import com.almarai.data.easy_pick_models.util.exhaustive
import com.almarai.easypick.R
import com.almarai.easypick.adapters.item.ProductsAdapter
import com.almarai.easypick.databinding.ScreenProductBinding
import com.almarai.easypick.extensions.*
import com.almarai.easypick.extensions.hideViewStateAlert
import com.almarai.easypick.extensions.setSearchView
import com.almarai.easypick.extensions.showViewStateAlert
import com.almarai.easypick.utils.BundleKeys
import com.almarai.easypick.utils.FilterFunnel
import com.almarai.easypick.utils.FilterScreenSource
import com.almarai.easypick.view_models.ProductListViewModel
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductListScreen(val adapter: ProductsAdapter) : Fragment(), SearchView.OnQueryTextListener {
    private val productListViewModel: ProductListViewModel by viewModel()
    private lateinit var navController: NavController
    private val args: ProductListScreenArgs by navArgs()
    private lateinit var screenProductBinding: ScreenProductBinding
    private lateinit var products: List<Product>
    private var searchView: SearchView? = null

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
        adapter.products = listOf()
        adapter.setProductScreenViewModel(productListViewModel)

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

        savedStateHandle?.getLiveData<Filters>(BundleKeys.FILTER_MODEL)?.observe(
            viewLifecycleOwner,
            Observer { result ->
                productListViewModel.filtersModel = result

                //Filter the list
                FilterFunnel(adapter, result).filterProducts(products)

                //Notify the adapter
                adapter.notifyDataSetChanged()

                //The filter list might take some time to filter, so add some lag to it
                viewLifecycleOwner.lifecycleScope.launch {
                    delay(700)
                    productListViewModel.setRouteServiceDetails(adapter.products)
                    productListViewModel.isFiltered.value = products.size != adapter.products.size
                }
            })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_product_screen, menu)
        (menu.findItem(R.id.menu_product_action_search).actionView as SearchView).setSearchView(
            this,
            R.string.hint_search_product, InputType.TYPE_CLASS_TEXT
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_product_action_filter -> {
                val action = ProductListScreenDirections
                    .actionProductListScreenToFilterScreen(
                        FilterScreenSource.ProductListScreen,
                        productListViewModel.filtersModel
                    )
                navController.navigate(action)
            }
            R.id.menu_product_action_save -> {
                val saveStateHandle = navController.previousBackStackEntry?.savedStateHandle

                //Set the result
                saveStateHandle?.set(
                    BundleKeys.ROUTE_PROCESSED,
                    Pair(args.SelectedRouteNumber, RouteStatus.Served)
                )

                navController.popBackStack()
            }
            R.id.menu_product_action_search -> {
                viewLifecycleOwner.lifecycleScope.launch {
                    delay(100)

                   hideKeyboard()
                }
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

        products = list
        adapter.products = list
        adapter.notifyDataSetChanged()

        productListViewModel.setRouteServiceDetails(list)
    }

    private fun animateUI() {
        val bottomToTop = AnimationUtils.loadAnimation(activity, R.anim.bottom_to_top)
        val topToBottom = AnimationUtils.loadAnimation(activity, R.anim.top_to_bottom)

        screenProductBinding.screenProductBackgroundImage.startAnimation(topToBottom)

        screenProductBinding.screenProductTotalItemsLabel.startAnimation(topToBottom)
        screenProductBinding.screenProductTotalItemsText.startAnimation(topToBottom)
        screenProductBinding.screenProductFiltered.startAnimation(topToBottom)
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
        recyclerView.adapter = this.adapter
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        //Show the product details dialog
        showProductDetail()

        return false
    }

    private fun showProductDetail() {
        if (adapter.products.size == 1) {
            val view = adapter.recyclerView.findViewHolderForAdapterPosition(0)?.itemView

            // Show this products details
            if (view != null) {
                adapter.showProductDetailsDialog(view, 0)
            }
        }
    }

    override fun onQueryTextChange(newText: String): Boolean {
        //Hide the keyboard
        hideKeyboard()

        //Filter the list
        FilterFunnel(adapter, Filters()).searchProducts(newText, products)
        return false
    }
}