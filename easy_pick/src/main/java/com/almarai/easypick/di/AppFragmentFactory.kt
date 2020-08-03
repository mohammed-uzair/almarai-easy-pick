package com.almarai.easypick.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.almarai.easypick.adapters.item.ProductsAdapter
import com.almarai.easypick.screens.*
import com.almarai.repository.api.ApplicationRepository
import javax.inject.Inject

class AppFragmentFactory @Inject constructor(
    private val applicationRepository: ApplicationRepository,

    //All screens adapters
    private val productsAdapter: ProductsAdapter

    //Todo - Pass adapters from HILT
    //Other screens adapters
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            LaunchScreen::class.java.name -> LaunchScreen(applicationRepository)
            OnBoardingScreen::class.java.name -> OnBoardingScreen(applicationRepository)
            NetworkConfigurationScreen::class.java.name -> NetworkConfigurationScreen()
            DataConfigurationScreen::class.java.name -> DataConfigurationScreen()
            HomeScreen::class.java.name -> HomeScreen(applicationRepository)
            RouteSelectionScreen::class.java.name -> RouteSelectionScreen()
            ProductListScreen::class.java.name -> ProductListScreen(productsAdapter)
            FilterScreen::class.java.name -> FilterScreen()

            else -> super.instantiate(classLoader, className)
        }
    }
}