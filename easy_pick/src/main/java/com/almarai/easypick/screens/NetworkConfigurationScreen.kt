package com.almarai.easypick.screens

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.almarai.data.easy_pick_models.NetworkConfiguration
import com.almarai.easypick.R
import com.almarai.easypick.view_models.NetworkConfigurationViewModel
import com.almarai.repository.utils.AppDataConfiguration
import kotlinx.android.synthetic.main.screen_network_configuration.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class NetworkConfigurationScreen : Fragment(R.layout.screen_network_configuration) {
    private val viewModel: NetworkConfigurationViewModel by viewModel()
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        init()
    }

    private fun init() {
        //Set screen title
        activity?.title = getString(R.string.title_network_configuration)

        setUIData()

        animateUI()

        screen_network_config_save_button.setOnClickListener {
            if (validateData()) {
                viewModel.saveNetworkConfiguration(
                    NetworkConfiguration(
                        screen_network_config_server_ip_edit_text.text.toString().trim(),
                        screen_network_config_port_edit_text.text.toString().trim()
                    )
                )

                when (viewModel.checkAppDataConfigurations()) {
                    AppDataConfiguration.DataConfiguration ->
                        navController.navigate(R.id.action_networkConfigurationScreen_to_dataConfigurationScreen)
                    AppDataConfiguration.Home ->
                        navController.navigate(R.id.action_networkConfigurationScreen_to_homeScreen)
                }
            }
        }
    }

    private fun setUIData() {
        val networkConfiguration = viewModel.getNetworkConfiguration()
        screen_network_config_server_ip_edit_text.setText(networkConfiguration.serverIpAddress)
        screen_network_config_port_edit_text.setText(networkConfiguration.serverPort)
    }

    private fun validateData(): Boolean {
        validateIpAddress()
        return true
    }

    private fun validateIpAddress() {

    }

    private fun animateUI() {
        val bottomToTop = AnimationUtils.loadAnimation(activity, R.anim.bottom_to_top)
        val topToBottom = AnimationUtils.loadAnimation(activity, R.anim.top_to_bottom)

        screen_network_config_background_image.startAnimation(topToBottom)
        screen_network_config_animation.startAnimation(topToBottom)

        screen_network_config_save_button.startAnimation(bottomToTop)

        screen_network_config_server_ip_edit_text.startAnimation(bottomToTop)
        screen_network_config_port_edit_text.startAnimation(bottomToTop)
    }
}
