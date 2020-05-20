package com.almarai.easypick.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.almarai.easypick.R
import com.almarai.easypick.view_models.DataConfigurationScreenViewModel
import kotlinx.android.synthetic.main.screen_data_configuration.*

class DataConfigurationScreen : Fragment() {
    lateinit var navController: NavController

    companion object {
        fun newInstance() = DataConfigurationScreen()
    }

    private lateinit var viewModel: DataConfigurationScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.screen_data_configuration, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DataConfigurationScreenViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        init()
    }

    private fun init() {
        //Set screen title
        activity?.title = getString(R.string.title_data_configuration)

        screen_data_config_save_button.setOnClickListener {
            navController.navigate(R.id.action_dataConfigurationScreen_to_homeScreen)
        }
    }
}
