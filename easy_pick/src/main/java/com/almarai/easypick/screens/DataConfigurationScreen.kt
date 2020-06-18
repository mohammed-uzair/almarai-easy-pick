package com.almarai.easypick.screens

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.almarai.data.easy_pick_models.DataConfiguration
import com.almarai.easypick.R
import com.almarai.easypick.utils.APP_SELECTED_THEME
import com.almarai.easypick.utils.AppTheme
import com.almarai.easypick.view_models.DataConfigurationViewModel
import com.almarai.repository.utils.AppDataConfiguration
import kotlinx.android.synthetic.main.screen_data_configuration.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DataConfigurationScreen : Fragment(R.layout.screen_data_configuration) {
    private val viewModel: DataConfigurationViewModel by viewModel()
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        init()
    }

    private fun init() {
        //Set screen title
        activity?.title = getString(R.string.title_data_configuration)

        setUIData()

        animateUI()

        screen_data_config_save_button.setOnClickListener {
            if (validateData()) {
                viewModel.saveDataConfiguration(
                    DataConfiguration(
                        screen_data_config_sales_date_edit_text.text.toString().trim(),
                        screen_data_config_depot_code_edit_text.text.toString().trim().toInt(),
                        screen_data_config_route_preference_edit_text.selectedItem.toString().trim()
                    )
                )

                when (viewModel.checkAppDataConfigurations()) {
                    AppDataConfiguration.NetworkConfiguration ->
                        navController.navigate(R.id.action_dataConfigurationScreen_to_networkConfigurationScreen)
                    AppDataConfiguration.Home ->
                        navController.navigate(R.id.action_dataConfigurationScreen_to_homeScreen)
                }
            }
        }
    }

    private fun setUIData() {
        val dataConfiguration = viewModel.getDataConfiguration()

        screen_data_config_sales_date_edit_text.setText(dataConfiguration.salesDate)
        screen_data_config_depot_code_edit_text.setText(dataConfiguration.depotCode.toString())

        val routeProductTypePreferences =
            resources.getStringArray(R.array.route_product_type_preferences)
        var selectedPreferenceId = 0
        for ((index, value) in routeProductTypePreferences.withIndex()) {
            if (value == dataConfiguration.routePreference) {
                selectedPreferenceId = index
                break
            }
        }

        screen_data_config_route_preference_edit_text.setSelection(selectedPreferenceId)
    }

    private fun validateData(): Boolean {
        return true
    }

    private fun animateUI() {
        val bottomToTop = AnimationUtils.loadAnimation(activity, R.anim.bottom_to_top)
        val topToBottom = AnimationUtils.loadAnimation(activity, R.anim.top_to_bottom)

        screen_data_config_background_image.startAnimation(topToBottom)
        screen_data_config_animation.startAnimation(topToBottom)

        screen_data_config_save_button.startAnimation(bottomToTop)

        screen_data_config_sales_date_edit_text.startAnimation(bottomToTop)
        screen_data_config_depot_code_edit_text.startAnimation(bottomToTop)
        screen_data_config_route_preference_edit_text.startAnimation(bottomToTop)

        when (APP_SELECTED_THEME) {
            is AppTheme.Light -> screen_data_config_animation.setAnimation(R.raw.anim_data_configuration_day)
            is AppTheme.Dark, AppTheme.Night -> screen_data_config_animation.setAnimation(R.raw.anim_data_configuration_night)
        }
    }
}