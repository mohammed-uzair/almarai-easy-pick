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
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.almarai.easypick.BuildConfig
import com.almarai.easypick.R
import com.almarai.easypick.databinding.ScreenLaunchBinding
import com.almarai.easypick.utils.BundleKeys
import com.almarai.easypick.view_models.LaunchViewModel
import com.almarai.repository.api.ApplicationRepository
import com.almarai.repository.utils.AppDataConfiguration
import org.koin.androidx.viewmodel.ext.android.viewModel

class LaunchScreen(private val applicationRepository: ApplicationRepository) : Fragment() {
    private val viewModel: LaunchViewModel by viewModel()
    private lateinit var navController: NavController
    private lateinit var screenLaunchBinding: ScreenLaunchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        screenLaunchBinding =
            DataBindingUtil.inflate(inflater, R.layout.screen_launch, container, false)
        screenLaunchBinding.apply {
            lifecycleOwner = this@LaunchScreen
            viewModel = this@LaunchScreen.viewModel
        }

        return screenLaunchBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        init()
    }

    override fun onResume() {
        super.onResume()

        //Hide the toolbar
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)?.supportActionBar?.show()
    }

    private fun init() {
        screenLaunchBinding.screenLaunchAppNameText.text = BuildConfig.APP_NAME

        animateUI()

        handleLaunchButtonClicked()

        handleOnBoardingCompletedResult()
    }

    private fun handleLaunchButtonClicked() {
        screenLaunchBinding.screenLaunchLaunchButton.setOnClickListener {
            if (!applicationRepository.isOnBoardingCompleted()) {
                navController.navigate(R.id.action_launchScreen_to_onBoardingScreen)
            } else {
                navigate()
            }
        }
    }

    private fun handleOnBoardingCompletedResult() {
        val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
        savedStateHandle?.getLiveData<Boolean>(BundleKeys.IS_ON_BOARDING_COMPLETED)?.observe(
            viewLifecycleOwner, Observer { result -> if (result) navigate() })
    }

    private fun navigate() {
        when (viewModel.checkAppDataConfigurations()) {
            AppDataConfiguration.NetworkConfiguration ->
                navController.navigate(R.id.action_launchScreen_to_networkConfigurationScreen)
            AppDataConfiguration.DataConfiguration ->
                navController.navigate(R.id.action_launchScreen_to_dataConfigurationScreen)
            AppDataConfiguration.Home ->
                navController.navigate(R.id.action_launchScreen_to_homeScreen)
        }
    }

    private fun animateUI() {
        val bottomToTop = AnimationUtils.loadAnimation(activity, R.anim.anim_bottom_to_top)
        val topToBottom = AnimationUtils.loadAnimation(activity, R.anim.anim_top_to_bottom)

        screenLaunchBinding.screenLaunchAnimation.startAnimation(bottomToTop)

        screenLaunchBinding.screenLaunchLaunchButton.startAnimation(bottomToTop)
        screenLaunchBinding.screenLaunchMadeWithLoveText.startAnimation(bottomToTop)
        screenLaunchBinding.screenLaunchEmployeeCodeText.startAnimation(bottomToTop)

        screenLaunchBinding.screenLaunchAppNameText.startAnimation(topToBottom)
        screenLaunchBinding.screenLaunchAppDescriptionText.startAnimation(topToBottom)
        screenLaunchBinding.screenLaunchAppVersionText.startAnimation(topToBottom)
    }
}
