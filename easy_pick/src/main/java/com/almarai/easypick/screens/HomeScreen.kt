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
import com.almarai.easypick.BuildConfig
import com.almarai.easypick.R
import com.almarai.easypick.databinding.ScreenHomeBinding
import com.almarai.easypick.view_models.HomeViewModel
import com.almarai.repository.api.ApplicationRepository
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeScreen(private val applicationRepository: ApplicationRepository) : Fragment(),
    View.OnClickListener {
    private val viewModel: HomeViewModel by viewModel()
    private lateinit var navController: NavController
    private lateinit var screenHomeBinding: ScreenHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        screenHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.screen_home, container, false)
        screenHomeBinding.apply {
            lifecycleOwner = this@HomeScreen
            viewModel = this@HomeScreen.viewModel
        }

        return screenHomeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        init()
    }

    private fun init() {
        //Set screen title
        activity?.title = getString(R.string.title_home)

        animateUI()

        screenHomeBinding.homeScreenRoutesButton.setOnClickListener(this)
        screenHomeBinding.homeScreenNetworkConfigurationButton.setOnClickListener(this)
        screenHomeBinding.homeScreenDataConfigurationButton.setOnClickListener(this)
        screenHomeBinding.homeScreenSettingsButton.setOnClickListener(this)
        screenHomeBinding.homeScreenStatisticsButton.setOnClickListener(this)
        screenHomeBinding.homeScreenExitAppButton.setOnClickListener(this)

        val appUpdate = applicationRepository.getAppUpdate()
        if (appUpdate != null && appUpdate.appVersionNumber > BuildConfig.VERSION_CODE) {
            screenHomeBinding.homeScreenSettingsButton.setCompoundDrawablesWithIntrinsicBounds(
                0,
                R.drawable.ic_settings_alert,
                0,
                0
            )
        } else {
            screenHomeBinding.homeScreenSettingsButton.setCompoundDrawablesWithIntrinsicBounds(
                0,
                R.drawable.ic_settings,
                0,
                0
            )
        }
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
        val topToBottom = AnimationUtils.loadAnimation(activity, R.anim.anim_top_to_bottom)
        val rightToLeft = AnimationUtils.loadAnimation(activity, R.anim.anim_right_to_left)
        val leftToRight = AnimationUtils.loadAnimation(activity, R.anim.anim_left_to_right)

        screenHomeBinding.screenHomeBackgroundImage.startAnimation(topToBottom)
        screenHomeBinding.screenHomeAnimation.startAnimation(topToBottom)

        screenHomeBinding.homeScreenRoutesButton.startAnimation(leftToRight)
        screenHomeBinding.homeScreenDataConfigurationButton.startAnimation(leftToRight)
        screenHomeBinding.homeScreenStatisticsButton.startAnimation(leftToRight)
        screenHomeBinding.homeScreenNetworkConfigurationButton.startAnimation(rightToLeft)
        screenHomeBinding.homeScreenSettingsButton.startAnimation(rightToLeft)
        screenHomeBinding.homeScreenExitAppButton.startAnimation(rightToLeft)
    }
}
