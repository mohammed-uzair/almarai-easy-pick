package com.almarai.easypick.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.almarai.easypick.R
import com.almarai.easypick.view_models.LoginScreenViewModel
import kotlinx.android.synthetic.main.screen_filter.*
import java.util.*

class FilterScreen : Fragment() {
    lateinit var navController: NavController

    private lateinit var viewModel: LoginScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.screen_filter, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginScreenViewModel::class.java)
    }

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
        val title = String.format(Locale.ENGLISH, getString(R.string.title_filter), "Route")
        filter_screen_title.text = title

        //Hide the toolbar for this screen
        (activity as AppCompatActivity).supportActionBar?.hide()

        filter_screen_back_button_image_view.setOnClickListener {
            navController.popBackStack()
        }
    }
}
