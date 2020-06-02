package com.almarai.easypick.screens

import android.content.res.Resources
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.almarai.easypick.MainActivity
import com.almarai.easypick.R
import com.almarai.easypick.view_models.HomeScreenViewModel
import kotlinx.android.synthetic.main.screen_home.*

class HomeScreen : Fragment(), View.OnClickListener {
    lateinit var navController: NavController

    companion object {
        fun newInstance() = HomeScreen()
    }

    private lateinit var viewModel: HomeScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.screen_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeScreenViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        init()
    }

    private fun init() {
        //Set screen title
        activity?.title = getString(R.string.title_home)

        setToolbarTheme()

        home_screen_routes_button.setOnClickListener(this)
        home_screen_network_configuration_button.setOnClickListener(this)
        home_screen_data_configuration_button.setOnClickListener(this)
        home_screen_settings_button.setOnClickListener(this)
        home_screen_statistics_button.setOnClickListener(this)
        home_screen_exit_app_button.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.home_screen_routes_button -> navController.navigate(R.id.action_homeScreen_to_routeSelectionScreen)
            R.id.home_screen_network_configuration_button -> navController.navigate(R.id.action_homeScreen_to_networkConfigurationScreen)
            R.id.home_screen_data_configuration_button -> navController.navigate(R.id.action_homeScreen_to_dataConfigurationScreen)
            R.id.home_screen_settings_button -> navController.navigate(R.id.action_homeScreen_to_settingsScreen)
            R.id.home_screen_statistics_button -> navController.navigate(R.id.action_homeScreen_to_statisticsScreen)
//            R.id.home_screen_exit_app_button -> navController.navigate(R.id.action_homeScreen_to_routeSelectionScreen)
        }
    }

    private fun setToolbarTheme() {
        // Get the primary text color of the theme
        val typedValue = TypedValue()
        val theme: Resources.Theme = requireActivity().theme
        theme.resolveAttribute(android.R.attr.textColorPrimary, typedValue, true)

        val arr = requireActivity().obtainStyledAttributes(
            typedValue.data, intArrayOf(
                android.R.attr.colorPrimary
            )
        )

        val arr1 = requireActivity().obtainStyledAttributes(
            typedValue.data, intArrayOf(
                R.attr.colorBackgroundTheme
            )
        )

        val titleAndIconsColor = arr.getColor(0, -1)
        val backgroundColor = arr1.getColor(0, -1)


        (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(
            ColorDrawable(backgroundColor)
        )

        val toolbar: Toolbar =
            (activity as MainActivity).findViewById(R.id.toolbar)

        toolbar.setTitleTextColor(titleAndIconsColor)

        val upArrow = resources.getDrawable(R.drawable.abc_ic_ab_back_material, theme)
        upArrow.setColorFilter(titleAndIconsColor, PorterDuff.Mode.SRC_ATOP);
        (activity as MainActivity).supportActionBar?.setHomeAsUpIndicator(upArrow)

        arr.recycle()

        requireActivity().actionBar?.themedContext?.setTheme(R.style.Theme_Base_ToolbarThemeLight)
    }
}
