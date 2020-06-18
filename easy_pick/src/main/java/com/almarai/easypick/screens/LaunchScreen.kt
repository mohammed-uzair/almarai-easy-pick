package com.almarai.easypick.screens

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.almarai.easypick.R
import com.almarai.easypick.view_models.LaunchViewModel
import com.almarai.repository.utils.AppDataConfiguration
import kotlinx.android.synthetic.main.screen_launch.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LaunchScreen : Fragment(R.layout.screen_launch) {
    private val viewModel: LaunchViewModel by viewModel()
    private lateinit var navController: NavController

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
        //Hide the toolbar
        (activity as AppCompatActivity).supportActionBar?.hide()

        animateUI()

        screen_launch_launch_button.setOnClickListener {
            when (viewModel.checkAppDataConfigurations()) {
                AppDataConfiguration.NetworkConfiguration ->
                    navController.navigate(R.id.action_launchScreen_to_networkConfigurationScreen)
                AppDataConfiguration.DataConfiguration ->
                    navController.navigate(R.id.action_launchScreen_to_dataConfigurationScreen)
                AppDataConfiguration.Home ->
                    navController.navigate(R.id.action_launchScreen_to_homeScreen)
            }
        }
    }

    private fun animateUI() {
        val bottomToTop = AnimationUtils.loadAnimation(activity, R.anim.bottom_to_top)
        val topToBottom = AnimationUtils.loadAnimation(activity, R.anim.top_to_bottom)

        screen_launch_animation.startAnimation(bottomToTop)

        screen_launch_launch_button.startAnimation(bottomToTop)
        screen_launch_made_with_love_text.startAnimation(bottomToTop)
        screen_launch_employee_code_text.startAnimation(bottomToTop)

        screen_launch_app_name_text.startAnimation(topToBottom)
        screen_launch_app_description_text.startAnimation(topToBottom)
        screen_launch_app_version_text.startAnimation(topToBottom)
    }
}
