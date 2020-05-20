package com.almarai.easypick.screens

import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.almarai.easypick.adapters.route.RoutesAdapter

class FragmentFactoryImpl(
    private val routesAdapter: RoutesAdapter
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String) =
        when (className) {
            LaunchScreen::class.java.name -> LaunchScreen()
            NetworkConfigurationScreen::class.java.name -> NetworkConfigurationScreen()
            DataConfigurationScreen::class.java.name -> DataConfigurationScreen()
            LoginScreen::class.java.name -> LoginScreen()
            HomeScreen::class.java.name -> HomeScreen()
            RouteSelectionScreen::class.java.name -> RouteSelectionScreen()
            ItemsScreen::class.java.name -> ItemsScreen()
            FilterScreen::class.java.name -> FilterScreen()
            else -> super.instantiate(classLoader, className)
        }
}