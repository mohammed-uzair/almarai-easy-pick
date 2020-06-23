package com.almarai.easypick.screens

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.almarai.easypick.R
import com.almarai.easypick.utils.FilterScreenSource
import com.almarai.easypick.utils.setTitle
import com.almarai.easypick.view_models.FilterViewModel
import kotlinx.android.synthetic.main.screen_filter.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilterScreen : Fragment(R.layout.screen_filter) {
    private val viewModel: FilterViewModel by viewModel()
    private val args: FilterScreenArgs by navArgs()
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        init()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)?.supportActionBar?.show()
    }

    private fun init() {
        //Set screen title
        if (args.FilterSource == FilterScreenSource.ProductListScreen)
            setTitle(R.string.title_filter_products)
        else
            setTitle(R.string.title_filter_routes)

        animateUI()
    }

    private fun animateUI() {
        val bottomToTop = AnimationUtils.loadAnimation(activity, R.anim.bottom_to_top)
        val topToBottom = AnimationUtils.loadAnimation(activity, R.anim.top_to_bottom)

        screen_filter_background_image.startAnimation(topToBottom)

        screen_filter_no_filter_radio.startAnimation(topToBottom)
        screen_filter_items_root.startAnimation(bottomToTop)
    }
}
