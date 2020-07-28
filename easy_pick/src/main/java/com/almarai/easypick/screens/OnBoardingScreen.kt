package com.almarai.easypick.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.almarai.easypick.R
import com.almarai.easypick.adapters.on_boarding.OnBoardingAdapter
import com.almarai.easypick.extensions.goBack
import com.almarai.easypick.utils.BundleKeys
import com.almarai.easypick.utils.on_boarding.OnBoardingCompletedListener
import com.almarai.repository.api.ApplicationRepository
import kotlinx.android.synthetic.main.screen_on_boarding.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OnBoardingScreen(private val applicationRepository: ApplicationRepository) : Fragment(),
    OnBoardingCompletedListener {
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.screen_on_boarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        screen_on_boarding_viewpager.adapter = OnBoardingAdapter(this)
    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
            delay(100)
            (activity as AppCompatActivity).supportActionBar?.hide()
        }
    }

    override fun onStop() {
        super.onStop()

        (activity as AppCompatActivity).supportActionBar?.show()
    }

    override fun onOnBoardingCompleted() {
        applicationRepository.setOnBoardingCompleted()

        //Set the result
        val saveStateHandle = navController.previousBackStackEntry?.savedStateHandle
        saveStateHandle?.set(BundleKeys.IS_ON_BOARDING_COMPLETED, true)

        goBack()
    }
}