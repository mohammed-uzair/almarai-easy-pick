package com.almarai.easypick.views.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.almarai.easypick.R
import com.almarai.easypick.databinding.ScreenNetworkConfigurationBinding
import com.almarai.easypick.views.utils.alert_dialog.showAlertDialog
import com.almarai.easypick.view_models.NetworkConfigurationViewModel
import com.almarai.easypick.voice.use_cases.NavigateScreen
import com.almarai.easypick.voice.use_cases.Screen
import com.almarai.repository.utils.AppDataConfiguration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NetworkConfigurationScreen : Fragment() {
    private val viewModel: NetworkConfigurationViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var screenNetworkConfigurationBinding: ScreenNetworkConfigurationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

        NavigateScreen.CURRENT_SCREEN = Screen.NetworkConfigurationScreen

        animateUI()

        screenNetworkConfigurationBinding.screenNetworkConfigSaveButton.setOnClickListener {
            if (validateNotEmptyValues()) {
                if (viewModel.saveNetworkConfiguration()) {
                    when (viewModel.checkAppDataConfigurations()) {
                        AppDataConfiguration.DataConfiguration ->
                            navController.navigate(R.id.action_networkConfigurationScreen_to_dataConfigurationScreen)
                        AppDataConfiguration.Home ->
                            navController.navigate(R.id.action_networkConfigurationScreen_to_homeScreen)
                    }
                }else{
                    showAlertDialog(alertMessage = R.string.alert_invalid_ip_format)
                }
            }
        }

//        screenNetworkConfigurationBinding.screenNetworkConfigServerIpEditText.addTextChangedListener(RegexMaskTextWatcher(IP_ADDRESS_REGEX))
    }

    private fun validateNotEmptyValues(): Boolean {
        val serverIp = viewModel.serverIp.value ?: ""
        val serverPort = viewModel.serverPort.value ?: ""

        if (serverIp.isBlank() || serverPort.isBlank()){
            showAlertDialog(alertMessage = R.string.alert_ip_port_empty)
            return false
        }
        return true
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
