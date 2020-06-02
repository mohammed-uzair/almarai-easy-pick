package com.almarai.easypick.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    lateinit var navController: NavController

    private lateinit var viewModel: StatisticsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.screen_statistics, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(StatisticsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        viewModel = ViewModelProvider(this).get(StatisticsViewModel::class.java)

        init()
    }

    private fun init() {
        //Set screen title
        activity?.title = getString(R.string.title_statistics)

        viewModel.mutableStatistics.observe(viewLifecycleOwner, Observer { statistics ->
            val commaAddedPagesCount =
                NumberFormat.getNumberInstance(Locale.US).format(statistics.physicalPagesSavedCount)
            login_screen_greetings_text.text = commaAddedPagesCount
        })
    }
}
