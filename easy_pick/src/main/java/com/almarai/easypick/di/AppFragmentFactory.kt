package com.almarai.easypick.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.almarai.easypick.common.AppUpdateFlow
import com.almarai.easypick.views.screens.*
import com.almarai.repository.api.ApplicationRepository
import javax.inject.Inject

class AppFragmentFactory @Inject constructor(
    private val appUpdateFlow: AppUpdateFlow,
    private val applicationRepository: ApplicationRepository
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            LaunchScreen::class.java.name -> LaunchScreen(applicationRepository)
            OnBoardingScreen::class.java.name -> OnBoardingScreen(applicationRepository)
            NetworkConfigurationScreen::class.java.name -> NetworkConfigurationScreen()
            DataConfigurationScreen::class.java.name -> DataConfigurationScreen()
            HomeScreen::class.java.name -> HomeScreen(applicationRepository)
            RouteSelectionScreen::class.java.name -> RouteSelectionScreen()
            FilterScreen::class.java.name -> FilterScreen()
            SettingsScreen::class.java.name -> SettingsScreen(appUpdateFlow)
            TicketScreen::class.java.name -> TicketScreen()

            else -> super.instantiate(classLoader, className)
        }
    }
}