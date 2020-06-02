package com.almarai.easypick.screens

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.almarai.easypick.R
import com.almarai.easypick.view_models.NetworkConfiurationScreenViewModel
import kotlinx.android.synthetic.main.screen_network_configuration.*

class NetworkConfigurationScreen : Fragment() {
    lateinit var navController: NavController

    companion object {
        fun newInstance() = NetworkConfigurationScreen()
    }

    private lateinit var viewModel: NetworkConfiurationScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.screen_network_configuration, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NetworkConfiurationScreenViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        init()
    }

    private fun init() {
        //Set screen title
        activity?.title = getString(R.string.title_network_configuration)

        setToolbarTheme()

        screen_network_config_save_button.setOnClickListener {
            navController.navigate(R.id.action_networkConfigurationScreen_to_dataConfigurationScreen)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_item_screen, menu)
    }

    private fun setToolbarTheme() {
        // Get the primary text color of the theme
//        val typedValue = TypedValue()
//        val theme: Resources.Theme = requireActivity().theme
//        theme.resolveAttribute(android.R.attr.textColorPrimary, typedValue, true)
//        val arr = requireActivity().obtainStyledAttributes(
//            typedValue.data, intArrayOf(
//                android.R.attr.colorPrimary
//            )
//        )
//        val primaryColor = arr.getColor(0, -1)
//
//
//        (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(
//            ColorDrawable(primaryColor)
//        )
//
//        val toolbar: Toolbar =
//            (activity as MainActivity).findViewById(R.id.toolbar)
//
//        toolbar.setTitleTextColor(resources.getColor(android.R.color.white, theme))
//
//        val upArrow = resources.getDrawable(R.drawable.ic_arrow_back, theme)
//        upArrow.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
//            Color.parseColor(
//                "#FFFFFF"
//            ), BlendModeCompat.SRC_ATOP
//        )
//
//        (activity as MainActivity).supportActionBar?.setHomeAsUpIndicator(upArrow)
//
//        arr.recycle()

        requireActivity().actionBar?.themedContext?.setTheme(R.style.Theme_Base_ToolbarTheme)
    }
}
