package com.almarai.easypick.screens

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.almarai.easypick.R
import com.almarai.easypick.view_models.LoginViewModel
import kotlinx.android.synthetic.main.screen_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginScreen : Fragment(R.layout.screen_login) {
    private val viewModel: LoginViewModel by viewModel()
    lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        init()
    }

    private fun init() {
        //Set screen title
        activity?.title = getString(R.string.title_login)

        animateUI()

//        screen_login_save_button.setOnClickListener {
//            navController.navigate(R.id.action_loginScreen_to_dataConfigurationScreen)
//        }
    }

    private fun animateUI() {
        val bottomToTop = AnimationUtils.loadAnimation(activity, R.anim.bottom_to_top)
        val topToBottom = AnimationUtils.loadAnimation(activity, R.anim.top_to_bottom)

        screen_login_background_image.startAnimation(topToBottom)
        screen_login_greetings_text.startAnimation(topToBottom)
        screen_login_user_name_text.startAnimation(topToBottom)
        screen_login_animation.startAnimation(topToBottom)

        screen_login_user_name_edit_text.startAnimation(bottomToTop)
        screen_login_user_password_edit_text.startAnimation(bottomToTop)

        screen_login_not_same_user_text.startAnimation(bottomToTop)
        screen_login_button.startAnimation(bottomToTop)
    }
}
