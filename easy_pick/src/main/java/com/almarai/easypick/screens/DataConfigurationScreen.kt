package com.almarai.easypick.screens

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.almarai.easypick.R
import com.almarai.easypick.databinding.ScreenDataConfigurationBinding
import com.almarai.easypick.utils.APP_SELECTED_THEME
import com.almarai.business.Utils.AppDateTimeFormat
import com.almarai.easypick.utils.AppTheme
import com.almarai.business.Utils.DateUtil
import com.almarai.easypick.view_models.DataConfigurationViewModel
import com.almarai.repository.utils.AppDataConfiguration
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class DataConfigurationScreen : Fragment() {
    private val viewModel: DataConfigurationViewModel by viewModel()
    private lateinit var navController: NavController
    private var salesDate = DateUtil.getCurrentDate(AppDateTimeFormat.formatDDMMYYYY)
    private lateinit var screenDataConfigurationBinding: ScreenDataConfigurationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        screenDataConfigurationBinding =
            DataBindingUtil.inflate(inflater, R.layout.screen_data_configuration, container, false)
        screenDataConfigurationBinding.apply {
            lifecycleOwner = this@DataConfigurationScreen
            viewModel = this@DataConfigurationScreen.viewModel
        }

        return screenDataConfigurationBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        init()
    }

    private fun init() {
        //Set screen title
        activity?.title = getString(R.string.title_data_configuration)

        animateUI()

        screenDataConfigurationBinding.screenDataConfigSaveButton.setOnClickListener {
            if (viewModel.saveDataConfiguration()) {
                when (viewModel.checkAppDataConfigurations()) {
                    AppDataConfiguration.NetworkConfiguration ->
                        navController.navigate(R.id.action_dataConfigurationScreen_to_networkConfigurationScreen)
                    AppDataConfiguration.Home ->
                        navController.navigate(R.id.action_dataConfigurationScreen_to_homeScreen)
                }
            }
        }

        screenDataConfigurationBinding.screenDataConfigSalesDateEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val calendarYear = calendar.get(Calendar.YEAR)
            val calendarMonthOfYear = calendar.get(Calendar.MONTH)
            val calendarDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            //Set the initial date
            val datePicker = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    salesDate = "$dayOfMonth/$month/$year"
                    viewModel.salesDate.value = salesDate
                },
                calendarYear,
                calendarMonthOfYear,
                calendarDayOfMonth
            )

            datePicker.show()
        }

        setUIData()
    }

    private fun setUIData() {
        val items = resources.getStringArray(R.array.route_product_type_preferences)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)
        screenDataConfigurationBinding.screenDataConfigRoutePreferenceSpinner.setAdapter(adapter)

//        screenDataConfigurationBinding.screenDataConfigRoutePreferenceSpinner.setOnItemClickListener { _, _, position, _ ->
//            viewModel.routeGroup.value =
//                screenDataConfigurationBinding.screenDataConfigRoutePreferenceSpinner.text.toString()
//        }

        viewModel.routeGroup.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            val routeProductTypePreferences =
                resources.getStringArray(R.array.route_product_type_preferences)
            var selectedPreferenceId = 0
            for ((index, value) in routeProductTypePreferences.withIndex()) {
                if (value == viewModel.routeGroup.value) {
                    selectedPreferenceId = index
                    break
                }
            }

            screenDataConfigurationBinding.screenDataConfigRoutePreferenceSpinner.setSelection(
                selectedPreferenceId
            )
        })
    }

    private fun animateUI() {
        val bottomToTop = AnimationUtils.loadAnimation(activity, R.anim.anim_bottom_to_top)
        val topToBottom = AnimationUtils.loadAnimation(activity, R.anim.anim_top_to_bottom)

        screenDataConfigurationBinding.screenDataConfigBackgroundImage.startAnimation(topToBottom)
        screenDataConfigurationBinding.screenDataConfigAnimation.startAnimation(topToBottom)

        screenDataConfigurationBinding.screenDataConfigSaveButton.startAnimation(bottomToTop)

        screenDataConfigurationBinding.screenDataConfigSalesDateEditTextLayout.startAnimation(
            bottomToTop
        )
        screenDataConfigurationBinding.screenDataConfigDepotCodeEditTextLayout.startAnimation(
            bottomToTop
        )
        screenDataConfigurationBinding.screenDataConfigRoutePreferenceSpinnerLayout.startAnimation(
            bottomToTop
        )

        when (APP_SELECTED_THEME) {
            is AppTheme.Light -> screenDataConfigurationBinding.screenDataConfigAnimation.setAnimation(
                R.raw.anim_data_configuration_day
            )
            is AppTheme.Dark, AppTheme.Night -> screenDataConfigurationBinding.screenDataConfigAnimation.setAnimation(
                R.raw.anim_data_configuration_night
            )
        }
    }
}