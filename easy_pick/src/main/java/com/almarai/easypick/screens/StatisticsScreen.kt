package com.almarai.easypick.screens

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.almarai.data.easy_pick_models.Result
import com.almarai.data.easy_pick_models.Statistics
import com.almarai.data.easy_pick_models.exhaustive
import com.almarai.easypick.R
import com.almarai.easypick.utils.Alert
import com.almarai.easypick.utils.hideAlert
import com.almarai.easypick.utils.showAlert
import com.almarai.easypick.view_models.StatisticsViewModel
import kotlinx.android.synthetic.main.screen_statistics.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.NumberFormat
import java.util.*

class StatisticsScreen : Fragment(R.layout.screen_statistics) {
    private val viewModel: StatisticsViewModel by viewModel()
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        init()
    }

    private fun init() {
        //Set screen title
        activity?.title = getString(R.string.title_statistics)

        animateUI()

        viewModel.statistics.observe(viewLifecycleOwner, Observer {
            it?.let { result ->
                when (result) {
                    is Result.Fetching -> showAlert(Alert.Loading)
                    is Result.Success -> showDataUi(result.data)
                    is Result.Error -> showAlert(Alert.Error)
                }.exhaustive
            }
        })
    }

    private fun showDataUi(statistics: Statistics) {
        //Hide the alert
        hideAlert()

        val commaAddedPagesCount =
            NumberFormat.getNumberInstance(Locale.US).format(statistics.physicalPagesSavedCount)
        screen_statistics_physical_pages_saved_text.text = commaAddedPagesCount
    }

    private fun animateUI() {
        val bottomToTop = AnimationUtils.loadAnimation(activity, R.anim.bottom_to_top)
        val topToBottom = AnimationUtils.loadAnimation(activity, R.anim.top_to_bottom)

        screen_statistics_background_image.startAnimation(topToBottom)
        screen_statistics_physical_pages_saved_text.startAnimation(topToBottom)
        screen_statistics_physical_pages_saved_text_summary_text.startAnimation(topToBottom)
    }
}
