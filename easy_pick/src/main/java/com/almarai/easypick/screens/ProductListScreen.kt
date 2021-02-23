package com.almarai.easypick.screens

import android.os.Bundle
import android.text.InputType
import android.view.*
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.almarai.data.easy_pick_models.Result
import com.almarai.data.easy_pick_models.filter.Filters
import com.almarai.data.easy_pick_models.product.Product
import com.almarai.data.easy_pick_models.route.RouteStatus
import com.almarai.data.easy_pick_models.util.exhaustive
import com.almarai.easypick.R
import com.almarai.easypick.adapters.item.ProductsAdapter
import com.almarai.easypick.databinding.ScreenProductBinding
import com.almarai.easypick.extensions.*
import com.almarai.easypick.utils.BundleKeys
import com.almarai.easypick.utils.FilterFunnel
import com.almarai.easypick.utils.FilterScreenSource
import com.almarai.easypick.utils.alert_dialog.OnNegativeButtonClickListener
import com.almarai.easypick.utils.alert_dialog.OnPositiveButtonClickListener
import com.almarai.easypick.utils.alert_dialog.hideAlertDialog
import com.almarai.easypick.utils.alert_dialog.showAlertDialog
import com.almarai.easypick.utils.progress.hideProgress
import com.almarai.easypick.utils.progress.showProgress
import com.almarai.easypick.view_models.ProductListViewModel
import com.google.android.material.transition.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Suppress("IMPLICIT_CAST_TO_ANY")
@AndroidEntryPoint
class ProductListScreen(val adapter: ProductsAdapter) : Fragment(), SearchView.OnQueryTextListener {
    private val productListViewModel: ProductListViewModel by viewModels()
    private var saveButton: MenuItem? = null
    private lateinit var navController: NavController
    private val args: ProductListScreenArgs by navArgs()
    private lateinit var screenProductBinding: ScreenProductBinding
    private lateinit var products: List<Product>

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
        activity?.title = "${getString(R.string.title_products_list)} - ${args.SelectedRouteNumber}"

        setHasOptionsMenu(true)

        animateUI()
        adapter.products = listOf()
        adapter.setProductScreenViewModel(productListViewModel)

        productListViewModel.getAllProducts(args.SelectedRouteNumber)
        observeProducts()

        setRecyclerView()

        handleFilterScreenResult()
        saveRouteProcessedResult(false)

        //A work around hack for the bug in the progress bar, that shows sometimes, do not comment this without doing monkey testing
        lifecycleScope.launch {
            delay(200)
            hideProgress()
        }

        setOnBackPressListener(object : OnBackPressListener {
            override fun onBackPressed() {
                showAlertDialog(
                    alertMessage = getString(R.string.alert_confirm_exit),
                    buttonPositiveText = R.string.alert_button_discard,
                    buttonNegativeText = R.string.alert_button_cancel,
                    animationResourceId = R.raw.anim_error,
                    positiveButtonClickListener = object : OnPositiveButtonClickListener {
                        override fun onClick() {
                            productListViewModel.discardChanges(args.SelectedRouteNumber)
                            goBack()
                            hideAlertDialog()
                        }
                    },
                    negativeButtonClickListener = object : OnNegativeButtonClickListener {
                        override fun onClick() {
                            hideAlertDialog()
                        }
                    }
                )
            }
        })
    }

    private fun handleFilterScreenResult() {
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

    private fun observeProducts() {
        productListViewModel.products.observe(viewLifecycleOwner, Observer {
            it?.let { result ->
                when (result) {
                    is Result.Fetching -> showViewStateAlert(
                        Alert.Loading,
                        R.string.fetching_products
                    )
                    is Result.Success -> showDataUi(result.data)
                    is Result.Error -> {
                        showViewStateAlert(Alert.Error, result.exceptionMessage)
                    }
                }.exhaustive
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_product_screen, menu)

        saveButton = menu.getItem(2)

        (menu.findItem(R.id.menu_product_action_search).actionView as SearchView).setSearchView(
            this,
            R.string.hint_search_product, InputType.TYPE_CLASS_TEXT
        )
    }

    private fun disableSaveButton(toggle: Boolean) {
        saveButton?.isVisible = !toggle
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
                disableSaveButton(true)

                //Save the data in the data source
                productListViewModel.updateRouteData(args.SelectedRouteNumber, products)

                observeRouteUpdated()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun saveRouteProcessedResult(isProcessed: Boolean) {
        val saveStateHandle = navController.previousBackStackEntry?.savedStateHandle

        //Set the result
        val result = if (isProcessed) {
            Pair(args.SelectedRouteNumber, RouteStatus.Served)
        } else Pair(-1, RouteStatus.NotServed)

        saveStateHandle?.set(BundleKeys.ROUTE_PROCESSED, result)
    }

    private fun observeRouteUpdated() {
        productListViewModel.routeDataUpdatedCategory.observe(viewLifecycleOwner, Observer {
            it?.let { result ->
                when (result) {
                    is Result.Fetching -> {
                        showProgress(R.string.progress_saving_route_details)
                    }
                    is Result.Success -> {
                        hideProgress()

                        saveRouteProcessedResult(true)

                        //Go back
                        goBack()
                    }
                    is Result.Error -> {
                        hideProgress()

                        disableSaveButton(false)

                        //Update the user
                        showAlertDialog(
                            R.string.error,
                            result.exceptionMessage,
                            animationResourceId = R.raw.anim_error
                        )
                    }
                }.exhaustive
            }
        })
    }

    private fun showDataUi(list: List<Product>) {
        //If the received data is empty
        if (list.isEmpty()) {
            showViewStateAlert(Alert.NoDataAvailable, R.string.no_data_available_products)
            return
        }

        //Hide the alert
        hideViewStateAlert()

        products = list
        adapter.products = list
        adapter.notifyDataSetChanged()

        productListViewModel.setRouteServiceDetails(list)

        setListFocus()
    }

    private fun animateUI() {
        val bottomToTop = AnimationUtils.loadAnimation(activity, R.anim.anim_bottom_to_top)
        val topToBottom = AnimationUtils.loadAnimation(activity, R.anim.anim_top_to_bottom)

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
        //hideKeyboard()

        //Filter the list
        FilterFunnel(adapter, Filters()).searchProducts(newText, products)
        return false
    }

    private fun setListFocus() =
        screenProductBinding.recyclerView.layoutMainRecyclerview
            .showFocus(lifecycleScope = viewLifecycleOwner.lifecycleScope)
}