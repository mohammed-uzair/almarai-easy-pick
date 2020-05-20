package com.almarai.easypick.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.almarai.easypick.R
import com.almarai.easypick.view_models.LoginScreenViewModel
import kotlinx.android.synthetic.main.screen_login.*

class FilterScreen : Fragment() {
    lateinit var navController: NavController

    companion object {
        fun newInstance() = FilterScreen()
    }

    private lateinit var viewModel: LoginScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.screen_filter, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginScreenViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        init()
    }

    private fun init(){
        //Set screen title
//        activity?.title = getString(R.string.title_login)

//        screen_login_save_button.setOnClickListener {
//            navController.navigate(R.id.action_loginScreen_to_dataConfigurationScreen)
//        }
    }
}
