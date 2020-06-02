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
import kotlinx.android.synthetic.main.screen_launch.*

class LaunchScreen : Fragment() {
    lateinit var navController: NavController

    private lateinit var viewModel: LaunchScreenViewModel

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)?.supportActionBar?.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.screen_launch, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LaunchScreenViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        init()
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
            navController.navigate(R.id.action_launchScreen_to_networkConfigurationScreen)

//            when {
//                !(viewModel.networkConfigurationCompleted()) -> {
//                    navController.navigate(R.id.action_launchScreen_to_networkConfiurationScreen)
//                }
//                !(viewModel.userLoggedIn()) -> {
//                    navController.navigate(R.id.action_launchScreen_to_networkConfiurationScreen)
//                }
//                !(viewModel.dataConfigurationCompleted()) -> {
//                    navController.navigate(R.id.action_launchScreen_to_networkConfiurationScreen)
//                }
//            }
        }
    }

    private fun animateUI() {
        val bottomUp = AnimationUtils.loadAnimation(activity, R.anim.bottom_up)
        val upBottom = AnimationUtils.loadAnimation(activity, R.anim.up_bottom)

        screen_launch_animation.startAnimation(bottomUp)

        screen_launch_launch_button.startAnimation(bottomUp)
        screen_launch_made_with_love_text.startAnimation(bottomUp)
        screen_launch_employee_code_text.startAnimation(bottomUp)

        screen_launch_app_name_text.startAnimation(upBottom)
        screen_launch_app_description_text.startAnimation(upBottom)
        screen_launch_app_version_text.startAnimation(upBottom)
    }
}
