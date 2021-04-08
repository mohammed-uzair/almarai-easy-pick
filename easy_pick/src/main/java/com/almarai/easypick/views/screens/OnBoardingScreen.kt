package com.almarai.easypick.views.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.almarai.easypick.R
import com.almarai.easypick.adapters.OnBoardingAdapter
import com.almarai.easypick.databinding.ScreenOnBoardingBinding
import com.almarai.easypick.extensions.goBack
import com.almarai.easypick.utils.BundleKeys
import com.almarai.easypick.views.utils.OnItemClickListener
import com.almarai.repository.api.ApplicationRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OnBoardingScreen(private val applicationRepository: ApplicationRepository) : Fragment() {
    private lateinit var navController: NavController
    private lateinit var screenOnBoardingBinding: ScreenOnBoardingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        screenOnBoardingBinding =
            DataBindingUtil.inflate(inflater, R.layout.screen_route_selection, container, false)

        return screenOnBoardingBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        screenOnBoardingBinding.screenOnBoardingViewpager.adapter =
            OnBoardingAdapter(object : OnItemClickListener {
                override fun onItemClick(item: Any?, position: Int) {
                    applicationRepository.setOnBoardingCompleted()

                    //Set the result
                    val saveStateHandle = navController.previousBackStackEntry?.savedStateHandle
                    saveStateHandle?.set(BundleKeys.IS_ON_BOARDING_COMPLETED, true)

                    goBack()
                }
            })
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
}