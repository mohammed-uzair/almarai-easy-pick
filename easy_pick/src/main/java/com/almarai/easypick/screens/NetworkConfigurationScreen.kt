package com.almarai.easypick.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.almarai.easypick.R
import com.almarai.easypick.databinding.ScreenNetworkConfigurationBinding
import com.almarai.easypick.view_models.NetworkConfigurationViewModel
import com.almarai.repository.utils.AppDataConfiguration
import org.koin.androidx.viewmodel.ext.android.viewModel

class NetworkConfigurationScreen : Fragment() {
    private val viewModel: NetworkConfigurationViewModel by viewModel()
    private lateinit var navController: NavController
    private lateinit var screenNetworkConfigurationBinding: ScreenNetworkConfigurationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        screenNetworkConfigurationBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.screen_network_configuration,
                container,
                false
            )
        screenNetworkConfigurationBinding.apply {
            lifecycleOwner = this@NetworkConfigurationScreen
            viewModel = this@NetworkConfigurationScreen.viewModel
        }

        return screenNetworkConfigurationBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        init()
    }

    private fun init() {
        //Set screen title
        activity?.title = getString(R.string.title_network_configuration)

        animateUI()

        screenNetworkConfigurationBinding.screenNetworkConfigSaveButton.setOnClickListener {
            if (viewModel.saveNetworkConfiguration()) {
                when (viewModel.checkAppDataConfigurations()) {
                    AppDataConfiguration.DataConfiguration ->
                        navController.navigate(R.id.action_networkConfigurationScreen_to_dataConfigurationScreen)
                    AppDataConfiguration.Home ->
                        navController.navigate(R.id.action_networkConfigurationScreen_to_homeScreen)
                }
            }
        }
    }

    private fun animateUI() {
        val bottomToTop = AnimationUtils.loadAnimation(activity, R.anim.anim_bottom_to_top)
        val topToBottom = AnimationUtils.loadAnimation(activity, R.anim.anim_top_to_bottom)

        screenNetworkConfigurationBinding.screenNetworkConfigBackgroundImage.startAnimation(
            topToBottom
        )
        screenNetworkConfigurationBinding.screenNetworkConfigAnimation.startAnimation(topToBottom)

        screenNetworkConfigurationBinding.screenNetworkConfigSaveButton.startAnimation(bottomToTop)

        screenNetworkConfigurationBinding.screenNetworkConfigServerIpEditTextLayout.startAnimation(
            bottomToTop
        )
        screenNetworkConfigurationBinding.screenNetworkConfigPortEditTextLayout.startAnimation(
            bottomToTop
        )
    }
}
