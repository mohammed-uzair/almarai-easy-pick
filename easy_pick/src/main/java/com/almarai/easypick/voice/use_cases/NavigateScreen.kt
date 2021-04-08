package com.almarai.easypick.voice.use_cases

import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import com.almarai.easypick.MainActivity
import com.almarai.easypick.views.screens.HomeScreenDirections
import kotlinx.android.synthetic.main.screen_home.*
import javax.inject.Inject

sealed class Screen {
    object Invalid : Screen()
    object GoBack : Screen()
    object DataConfigurationScreen : Screen()
    object NetworkConfigurationScreen : Screen()
    object RouteSelectionScreen : Screen()
    object ProductsScreen : Screen()
    object HomeScreen : Screen()
    object SettingsScreen : Screen()
}

class NavigateScreen @Inject constructor() {
    private var activity: Activity? = null

    companion object {
        const val TAG = "NavigateScreen"

        var CURRENT_SCREEN: Screen = Screen.Invalid
    }

    fun navigateToScreen(activity: Activity?, command: String) {
        this.activity = activity

        val screen = getScreenName(command)
        navigate(screen)
    }

    private fun getScreenName(command: String): Screen {
        return when {
            command.contains("dataconfiguration") -> Screen.DataConfigurationScreen
            command.contains("networkconfiguration") -> Screen.NetworkConfigurationScreen
            command.contains("route") -> Screen.RouteSelectionScreen
            command.contains("back") -> Screen.GoBack
            command.contains("setting") -> Screen.SettingsScreen
            else -> Screen.Invalid
        }
    }

    private fun navigate(screen: Screen) {
        val view = (activity as MainActivity).screenMainBinding.fragmentContainerHostFragment.rootView

        if (view != null) {
            when (screen) {
                Screen.GoBack -> goBack(view)
                Screen.DataConfigurationScreen -> navigateToDataConfigurationScreen(view)
                Screen.NetworkConfigurationScreen -> navigateToNetworkConfigurationScreen(view)
                Screen.RouteSelectionScreen -> {
                    val action = HomeScreenDirections
                        .actionHomeScreenToNetworkConfigurationScreen()
                    (activity as MainActivity).findNavController(view.id)
                        .navigate(action)
                }
                else -> showAlert()
            }
        }
    }

    private fun navigateToDataConfigurationScreen(view: View) {
        when (CURRENT_SCREEN) {
            Screen.GoBack -> goBack(view)

            Screen.HomeScreen -> {
                val action = HomeScreenDirections
                    .actionHomeScreenToDataConfigurationScreen()
                (activity as MainActivity).findNavController(view.id)
                    .navigate(action)
            }

            Screen.RouteSelectionScreen,
            Screen.NetworkConfigurationScreen,
            Screen.SettingsScreen -> {
                goBack(view)
                navigate(Screen.SettingsScreen)
            }

            Screen.ProductsScreen -> showAlert()
            else -> {}
        }
    }

    private fun navigateToNetworkConfigurationScreen(view: View) {
        when (CURRENT_SCREEN) {
            Screen.GoBack -> goBack(view)

            Screen.HomeScreen -> {
                val action = HomeScreenDirections
                    .actionHomeScreenToNetworkConfigurationScreen()
                (activity as MainActivity).findNavController(view.id)
                    .navigate(action)
            }

            Screen.RouteSelectionScreen,
            Screen.DataConfigurationScreen,
            Screen.SettingsScreen -> {
                goBack(view)
                navigate(Screen.SettingsScreen)
            }

            Screen.ProductsScreen -> showAlert()
            else -> {}
        }
    }

    private fun showAlert() {
        Toast.makeText(
            activity?.applicationContext,
            "Navigation not supported from this screen",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun goBack(view: View) {
        (activity as MainActivity).findNavController(view.id).popBackStack()
    }
}