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
import com.almarai.easypick.databinding.ScreenLoginBinding
import com.almarai.easypick.view_models.LoginViewModel
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginScreen : Fragment() {
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var screenLoginBinding: ScreenLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        screenLoginBinding =
            DataBindingUtil.inflate(inflater, R.layout.screen_data_configuration, container, false)
        screenLoginBinding.apply {
            lifecycleOwner = this@LoginScreen
            viewModel = this@LoginScreen.viewModel
        }

        return screenLoginBinding.root
    }


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
        val bottomToTop = AnimationUtils.loadAnimation(activity, R.anim.anim_bottom_to_top)
        val topToBottom = AnimationUtils.loadAnimation(activity, R.anim.anim_top_to_bottom)

        screenLoginBinding.screenLoginBackgroundImage.startAnimation(topToBottom)
        screenLoginBinding.screenLoginGreetingsText.startAnimation(topToBottom)
        screenLoginBinding.screenLoginUserNameText.startAnimation(topToBottom)
        screenLoginBinding.screenLoginAnimation.startAnimation(topToBottom)

        screenLoginBinding.screenLoginUserPasswordEditTextLayout.startAnimation(bottomToTop)
        screenLoginBinding.screenLoginUserPasswordEditTextLayout.startAnimation(bottomToTop)

        screenLoginBinding.screenLoginNotSameUserText.startAnimation(bottomToTop)
        screenLoginBinding.screenLoginLogoutText.startAnimation(bottomToTop)
        screenLoginBinding.screenLoginButton.startAnimation(bottomToTop)
    }
}
