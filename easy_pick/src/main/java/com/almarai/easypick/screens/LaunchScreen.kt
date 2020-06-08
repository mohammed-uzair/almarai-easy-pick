package com.almarai.easypick.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.almarai.easypick.R
import com.almarai.easypick.view_models.LaunchScreenViewModel
import com.almarai.repository.utils.AppDataConfiguration
import kotlinx.android.synthetic.main.screen_launch.*

class LaunchScreen : Fragment() {
    private lateinit var navController: NavController

    private lateinit var viewModel: LaunchScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(LaunchScreenViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.screen_launch, container, false)
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
        //Hide the toolbar
        (activity as AppCompatActivity).supportActionBar?.hide()

        screen_launch_made_with_love_text.text =
            HtmlCompat.fromHtml(
                getString(R.string.love_from_almarai),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )

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
