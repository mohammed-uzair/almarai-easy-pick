package com.almarai.easypick.screens

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.almarai.easypick.R
import com.almarai.easypick.view_models.NetworkConfiurationScreenViewModel
import kotlinx.android.synthetic.main.screen_network_configuration.*

class NetworkConfigurationScreen : Fragment() {
    lateinit var navController: NavController

    companion object {
        fun newInstance() = NetworkConfigurationScreen()
    }

    private lateinit var viewModel: NetworkConfiurationScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.screen_network_configuration, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NetworkConfiurationScreenViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        init()
    }

    private fun init() {
        //Set screen title
        activity?.title = getString(R.string.title_network_configuration)

        screen_network_config_save_button.setOnClickListener {
            navController.navigate(R.id.action_networkConfigurationScreen_to_dataConfigurationScreen)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_item_screen, menu)
    }
}
