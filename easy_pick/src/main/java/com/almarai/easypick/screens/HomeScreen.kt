package com.almarai.easypick.screens

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.almarai.easypick.R
import com.almarai.easypick.view_models.HomeViewModel
import kotlinx.android.synthetic.main.screen_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeScreen : Fragment(R.layout.screen_home), View.OnClickListener {
    private val viewModel: HomeViewModel by viewModel()
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        init()
    }

    private fun init() {
        //Set screen title
        activity?.title = getString(R.string.title_home)

        animateUI()

        home_screen_routes_button.setOnClickListener(this)
        home_screen_network_configuration_button.setOnClickListener(this)
        home_screen_data_configuration_button.setOnClickListener(this)
        home_screen_settings_button.setOnClickListener(this)
        home_screen_statistics_button.setOnClickListener(this)
        home_screen_exit_app_button.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.home_screen_routes_button -> navController.navigate(R.id.action_homeScreen_to_routeSelectionScreen)
            R.id.home_screen_network_configuration_button -> navController.navigate(R.id.action_homeScreen_to_networkConfigurationScreen)
            R.id.home_screen_data_configuration_button -> navController.navigate(R.id.action_homeScreen_to_dataConfigurationScreen)
            R.id.home_screen_settings_button -> navController.navigate(R.id.action_homeScreen_to_settingsScreen)
            R.id.home_screen_statistics_button -> navController.navigate(R.id.action_homeScreen_to_statisticsScreen)
//            R.id.home_screen_exit_app_button -> navController.navigate(R.id.action_homeScreen_to_testScreen)
        }
    }

    private fun animateUI() {
        val topToBottom = AnimationUtils.loadAnimation(activity, R.anim.top_to_bottom)
        val rightToLeft = AnimationUtils.loadAnimation(activity, R.anim.right_to_left)
        val leftToRight = AnimationUtils.loadAnimation(activity, R.anim.left_to_right)

        screen_home_background_image.startAnimation(topToBottom)
        screen_home_animation.startAnimation(topToBottom)

        home_screen_routes_button.startAnimation(leftToRight)
        home_screen_data_configuration_button.startAnimation(leftToRight)
        home_screen_statistics_button.startAnimation(leftToRight)
        home_screen_network_configuration_button.startAnimation(rightToLeft)
        home_screen_settings_button.startAnimation(rightToLeft)
        home_screen_exit_app_button.startAnimation(rightToLeft)
    }
}
