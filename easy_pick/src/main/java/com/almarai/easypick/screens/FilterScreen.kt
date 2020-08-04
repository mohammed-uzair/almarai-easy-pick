package com.almarai.easypick.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.almarai.easypick.R
import com.almarai.easypick.databinding.ScreenFilterBinding
import com.almarai.easypick.extensions.OnBackPressListener
import com.almarai.easypick.extensions.goBack
import com.almarai.easypick.extensions.setOnBackPressListener
import com.almarai.easypick.utils.BundleKeys
import com.almarai.easypick.utils.FilterScreenSource
import com.almarai.easypick.utils.setTitle
import com.almarai.easypick.view_models.FilterViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterScreen : Fragment() {
    private val viewModel: FilterViewModel by viewModels()
    private val args: FilterScreenArgs by navArgs()
    private lateinit var navController: NavController
    private lateinit var screenFilterBinding: ScreenFilterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        screenFilterBinding =
            DataBindingUtil.inflate(inflater, R.layout.screen_filter, container, false)
        screenFilterBinding.apply {
            lifecycleOwner = this@FilterScreen
            viewModel = this@FilterScreen.viewModel
        }

        return screenFilterBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        init()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)?.supportActionBar?.show()
    }

    private fun init() {
        animateUI()

        getBundleArguments()

        setOnBackPressListener(object : OnBackPressListener {
            override fun onBackPressed() {
                if (viewModel.persistFilters.value == true) viewModel.persistFilters()

                val saveStateHandle = navController.previousBackStackEntry?.savedStateHandle

                //Set the result
                saveStateHandle?.set(BundleKeys.FILTER_MODEL, viewModel.getFilterModel())

                goBack()
            }
        })

        addObservers()

        //A little hack for views not adjusting in time, was causing a time miss match errors
        viewLifecycleOwner.lifecycleScope.launch {
            delay(300)
            val filterModel = args.FilterModel
            if (filterModel != null) viewModel.setFilterModel(filterModel)
        }
    }

    private fun getBundleArguments() {
        //Set screen title
        if (args.FilterSource == FilterScreenSource.ProductListScreen)
            setTitle(R.string.title_filter_products)
        else
            setTitle(R.string.title_filter_routes)
    }

    private fun animateUI() {
        val bottomToTop = AnimationUtils.loadAnimation(activity, R.anim.anim_bottom_to_top)
        val topToBottom = AnimationUtils.loadAnimation(activity, R.anim.anim_top_to_bottom)

        screenFilterBinding.screenFilterBackgroundImage.startAnimation(topToBottom)

        screenFilterBinding.screenFilterNoFilterRadio.startAnimation(topToBottom)
        screenFilterBinding.screenFilterItemsRoot.startAnimation(bottomToTop)
    }

    private fun addObservers() {
        //region Observers
        viewModel.noFilter.observe(
            viewLifecycleOwner,
            Observer { if (it) viewModel.unCheckAllFilters() })

        viewModel.filterByAllSubCategory1.observe(
            viewLifecycleOwner,
            Observer { viewModel.toggleSubCategory1(it) })

        viewModel.filterByAllSubCategory2.observe(
            viewLifecycleOwner,
            Observer { viewModel.toggleSubCategory2(it) })

        viewModel.sortOrderAscending.observe(viewLifecycleOwner,
            Observer {
                if (it) {
                    viewModel.unCheckNoFilter()
                    viewModel.checkSortWith()
                } else if (viewModel.noFilter.value == false) viewModel.checkNoFilterState()
            })
        viewModel.sortOrderDescending.observe(viewLifecycleOwner,
            Observer {
                if (it) {
                    viewModel.unCheckNoFilter()
                    viewModel.checkSortWith()
                } else if (viewModel.noFilter.value == false) viewModel.checkNoFilterState()
            })
        viewModel.sortWithXNumber.observe(viewLifecycleOwner,
            Observer {
                if (it) {
                    viewModel.unCheckNoFilter()
                    viewModel.checkSortIn()
                } else if (viewModel.noFilter.value == false) viewModel.checkNoFilterState()
            })
        viewModel.sortWithXDescription.observe(viewLifecycleOwner,
            Observer {
                if (it) {
                    viewModel.unCheckNoFilter()
                    viewModel.checkSortIn()
                } else if (viewModel.noFilter.value == false) viewModel.checkNoFilterState()
            })
        viewModel.statusServed.observe(viewLifecycleOwner,
            Observer {
                if (it) {
                    viewModel.unCheckNoFilter()
                    viewModel.checkSortIn()
                } else if (viewModel.noFilter.value == false) viewModel.checkNoFilterState()
            })
        viewModel.filterBySubCategory1Dairy.observe(viewLifecycleOwner,
            Observer { if (it) viewModel.unCheckNoFilter() else if (viewModel.noFilter.value == false) viewModel.checkNoFilterState() })
        viewModel.filterBySubCategory1Poultry.observe(viewLifecycleOwner,
            Observer { if (it) viewModel.unCheckNoFilter() else if (viewModel.noFilter.value == false) viewModel.checkNoFilterState() })
        viewModel.filterBySubCategory1Bakery.observe(viewLifecycleOwner,
            Observer { if (it) viewModel.unCheckNoFilter() else if (viewModel.noFilter.value == false) viewModel.checkNoFilterState() })
        viewModel.filterBySubCategory2IPNC.observe(viewLifecycleOwner,
            Observer { if (it) viewModel.unCheckNoFilter() else if (viewModel.noFilter.value == false) viewModel.checkNoFilterState() })
        viewModel.filterBySubCategory2NonIPNC.observe(viewLifecycleOwner,
            Observer { if (it) viewModel.unCheckNoFilter() else if (viewModel.noFilter.value == false) viewModel.checkNoFilterState() })
        viewModel.customerOnly.observe(viewLifecycleOwner,
            Observer { if (it) viewModel.unCheckNoFilter() else if (viewModel.noFilter.value == false) viewModel.checkNoFilterState() })
        viewModel.allowMultipleFilters.observe(viewLifecycleOwner,
            Observer { if (it) viewModel.unCheckNoFilter() else if (viewModel.noFilter.value == false) viewModel.checkNoFilterState() })
        //endregion
    }
}
