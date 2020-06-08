package com.almarai.easypick.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.almarai.easypick.R
import com.almarai.easypick.view_models.StatisticsViewModel
import kotlinx.android.synthetic.main.screen_statistics.*
import java.text.NumberFormat
import java.util.*

class StatisticsScreen : Fragment() {
    private lateinit var navController: NavController

    private lateinit var viewModel: StatisticsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(StatisticsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.screen_statistics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        init()
    }

    private fun init() {
        //Set screen title
        activity?.title = getString(R.string.title_statistics)

        animateUI()

        viewModel.mutableStatistics.observe(viewLifecycleOwner, Observer { statistics ->
            val commaAddedPagesCount =
                NumberFormat.getNumberInstance(Locale.US).format(statistics.physicalPagesSavedCount)
            screen_statistics_physical_pages_saved_text.text = commaAddedPagesCount
        })
    }

    private fun animateUI() {
        val bottomToTop = AnimationUtils.loadAnimation(activity, R.anim.bottom_to_top)
        val topToBottom = AnimationUtils.loadAnimation(activity, R.anim.top_to_bottom)

        screen_statistics_background_image.startAnimation(topToBottom)
        screen_statistics_physical_pages_saved_text.startAnimation(topToBottom)
        screen_statistics_physical_pages_saved_text_summary_text.startAnimation(topToBottom)
    }
}
