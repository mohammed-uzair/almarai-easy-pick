package com.almarai.easypick.screens

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.*
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.almarai.data.easy_pick_models.Result
import com.almarai.data.easy_pick_models.filter.Filters
import com.almarai.data.easy_pick_models.route.Route
import com.almarai.data.easy_pick_models.route.RouteServiceStatus
import com.almarai.data.easy_pick_models.route.RouteStatus
import com.almarai.data.easy_pick_models.util.exhaustive
import com.almarai.easypick.R
import com.almarai.easypick.adapters.route.RoutesAdapter
import com.almarai.easypick.databinding.ScreenRouteSelectionBinding
import com.almarai.easypick.extensions.*
import com.almarai.easypick.utils.BundleKeys
import com.almarai.easypick.utils.FilterFunnel
import com.almarai.easypick.utils.FilterScreenSource
import com.almarai.easypick.view_models.RouteSelectionViewModel
import com.almarai.machine_learning.LiveBarcodeScanningActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.ExecutorService

class RouteSelectionScreen : Fragment(), SearchView.OnQueryTextListener {
    private val routesViewModel: RouteSelectionViewModel by viewModel()
    private lateinit var navController: NavController
    private val adapter by lazy { RoutesAdapter() }
    private lateinit var screenRouteSelectionBinding: ScreenRouteSelectionBinding
    private lateinit var routes: List<Route>


//    private var preview: Preview? = null
//
//    //    private var imageCapture: ImageCapture? = null
////    private var imageAnalyzer: ImageAnalysis? = null
//    private var camera: Camera? = null

    //    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return bindUIScreen(inflater, container)
    }

    private fun bindUIScreen(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): View {
        screenRouteSelectionBinding =
            DataBindingUtil.inflate(inflater, R.layout.screen_route_selection, container, false)
        screenRouteSelectionBinding.apply {
            lifecycleOwner = this@RouteSelectionScreen
            viewModel = this@RouteSelectionScreen.routesViewModel
        }

        return screenRouteSelectionBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        init()
    }

    private fun init() {
        //Set screen title
        activity?.title = getString(R.string.title_route_selection)

        setHasOptionsMenu(true)

        animateUI()

        observeRoutes()
        observeRoutesStatuses()

        setRecyclerView()

        handleOtherScreenResults()

        setRoutesRefreshListener()
    }

    private fun setRoutesRefreshListener() {
        screenRouteSelectionBinding.screenRouteSelectionRefresh.setOnRefreshListener {
            //Get only the routes status from data source
            routesViewModel.getAllRouteStatus()

            //Update the adapter for the data change
            screenRouteSelectionBinding.screenRouteSelectionRefresh.isRefreshing = false
        }
    }

    private fun handleOtherScreenResults() {
        val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle

        handleRouteProcessedFromProductsScreen(savedStateHandle)
        handleFiltersFromFilterScreen(savedStateHandle)
    }

    private fun handleRouteProcessedFromProductsScreen(savedStateHandle: SavedStateHandle?) {
        savedStateHandle?.getLiveData<Pair<Int, RouteStatus>>(BundleKeys.ROUTE_PROCESSED)?.observe(
            viewLifecycleOwner,
            Observer { result ->
                val position = routesViewModel.updateRouteProcessed(result)

                if (position != RecyclerView.NO_POSITION) adapter.notifyItemChanged(position)
            })
    }

    private fun handleFiltersFromFilterScreen(savedStateHandle: SavedStateHandle?) {
        savedStateHandle?.getLiveData<Filters>(BundleKeys.FILTER_MODEL)?.observe(
            viewLifecycleOwner,
            Observer { result ->
                routesViewModel.filtersModel = result

                //Filter the list
                FilterFunnel(adapter, result).filterRoutes(routes)

                //Notify the adapter
                adapter.notifyDataSetChanged()

                //The filter list might take some time to filter, so add some lag to it
                viewLifecycleOwner.lifecycleScope.launch {
                    delay(700)
                    routesViewModel.setRouteServiceDetails(adapter.routes)
                    routesViewModel.isFiltered.value = routes.size != adapter.routes.size
                }
            })
    }

    private fun observeRoutes() {
        routesViewModel.routes.observe(viewLifecycleOwner, Observer {
            it?.let { result ->
                when (result) {
                    is Result.Fetching -> showViewStateAlert(
                        Alert.Loading,
                        R.string.fetching_routes
                    )
                    is Result.Success -> showDataUi(result.data)
                    is Result.Error -> showViewStateAlert(Alert.Error)
                }.exhaustive
            }
        })
    }

    private fun observeRoutesStatuses() {
        routesViewModel.routesServiceStatus.observe(viewLifecycleOwner, Observer {
            it?.let { result ->
                when (result) {
                    is Result.Fetching -> screenRouteSelectionBinding.screenRouteSelectionRefresh.isRefreshing =
                        true
                    is Result.Success -> updateRoutesStatuses(result.data)
                    is Result.Error -> screenRouteSelectionBinding.screenRouteSelectionRefresh.isRefreshing =
                        false
                }.exhaustive
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_routes_screen, menu)
        (menu.findItem(R.id.menu_route_action_search).actionView as SearchView).setSearchView(
            this,
            R.string.hint_search_route, InputType.TYPE_CLASS_TEXT
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_route_action_filter -> {
                val action = RouteSelectionScreenDirections
                    .actionRouteSelectionScreenToFilterScreen(
                        FilterScreenSource.RouteSelectionScreenScreen,
                        routesViewModel.filtersModel
                    )
                navController.navigate(action)
            }
            R.id.menu_route_action_search -> {
                viewLifecycleOwner.lifecycleScope.launch {
                    delay(100)

                    //Close the keyboard on load
                    val imm: InputMethodManager =
                        requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(item.actionView.windowToken, 0)
                }
            }
            R.id.menu_route_action_barcode_search -> {
                startActivity(Intent(activity, LiveBarcodeScanningActivity::class.java))
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun updateRoutesStatuses(list: List<RouteServiceStatus>) {
        if (list.isNotEmpty()) {
            //Update all routes status
            list.forEach { route ->
                routes.first { it.number == route.number }.serviceStatus = route.status
            }

            //Notify the adapter
            adapter.notifyDataSetChanged()
        }

        screenRouteSelectionBinding.screenRouteSelectionRefresh.isRefreshing = false
    }

    private fun showDataUi(list: List<Route>) {
        //If the received data is empty
        if (list.isEmpty()) {
            showViewStateAlert(Alert.NoDataAvailable, R.string.no_data_available_routes)
            return
        }

        //Hide the alert
        hideViewStateAlert()

        routes = list
        adapter.setData(list, this, routesViewModel)
        adapter.notifyDataSetChanged()

        routesViewModel.setRouteServiceDetails(list)

        setListFocus()
    }

    private fun animateUI() {
        val bottomToTop = AnimationUtils.loadAnimation(activity, R.anim.bottom_to_top)
        val topToBottom = AnimationUtils.loadAnimation(activity, R.anim.top_to_bottom)

        screenRouteSelectionBinding.screenRouteSelectionBackgroundImage.startAnimation(topToBottom)

        screenRouteSelectionBinding.screenRouteSelectionServicedLabel.startAnimation(topToBottom)
        screenRouteSelectionBinding.screenRouteSelectionServicedText.startAnimation(topToBottom)
        screenRouteSelectionBinding.screenProductFiltered.startAnimation(topToBottom)
        screenRouteSelectionBinding.screenRouteSelectionServingLabel.startAnimation(topToBottom)
        screenRouteSelectionBinding.screenRouteSelectionServingText.startAnimation(topToBottom)

        screenRouteSelectionBinding.recyclerView.layoutMainRecyclerview.startAnimation(bottomToTop)
    }

    private fun setRecyclerView() {
        val recyclerView = screenRouteSelectionBinding.recyclerView.layoutMainRecyclerview
        recyclerView.animation = null
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = this.adapter
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        //Show the product details dialog

        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        //Filter the list
        FilterFunnel(adapter, Filters()).searchRoutes(newText, routes)
        return false
    }

    private fun setListFocus() =
        screenRouteSelectionBinding.recyclerView.layoutMainRecyclerview
            .showFocus(lifecycleScope = viewLifecycleOwner.lifecycleScope)

    /* //region CameraX
     private fun startCamera() {
         val cameraProviderFuture =
             androidx.camera.lifecycle.ProcessCameraProvider.getInstance(requireContext())

         cameraProviderFuture.addListener(Runnable {
             // Used to bind the lifecycle of cameras to the lifecycle owner
             val cameraProvider: androidx.camera.lifecycle.ProcessCameraProvider =
                 cameraProviderFuture.get()

             // Preview
             preview = Preview.Builder()
                 .build()

             // Select back camera
             val cameraSelector =
                 CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK)
                     .build()

             try {
                 // Unbind use cases before rebinding
                 cameraProvider.unbindAll()

                 // Bind use cases to camera
                 camera = cameraProvider.bindToLifecycle(
                     this, cameraSelector, preview
                 )
                 preview?.setSurfaceProvider(screenRouteSelectionBinding.viewFinder.createSurfaceProvider())
             } catch (exc: Exception) {
                 Log.e(TAG, "Use case binding failed", exc)
             }

         }, ContextCompat.getMainExecutor(requireContext()))
     }

     private class YourImageAnalyzer(private val context: Context) : ImageAnalysis.Analyzer {
         @SuppressLint("UnsafeExperimentalUsageError")
         override fun analyze(imageProxy: ImageProxy) {
             val mediaImage = imageProxy.image
             if (mediaImage != null) {
                 val image =
                     InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

                 // Pass image to an ML Kit Vision API
                 val options = BarcodeScannerOptions.Builder()
                     .setBarcodeFormats(
                         Barcode.FORMAT_QR_CODE,
                         Barcode.FORMAT_AZTEC
                     )
                     .build()
                 val scanner = BarcodeScanning.getClient(options)

                 val result = scanner.process(image)
                     .addOnSuccessListener { barcodes ->
                         // Task completed successfully
                         val rawValue = barcodes[0].rawValue
                         Toast.makeText(
                             context,
                             "Scanning barcode success - Raw -> $rawValue  Barcode -> ${barcodes[0]}",
                             Toast.LENGTH_LONG
                         )
                             .show()
                     }
                     .addOnFailureListener {
                         Toast.makeText(context, "Scanning barcode failed", Toast.LENGTH_LONG)
                             .show()
                     }
             }
         }
     }*/
    //endregion

    companion object {
        const val TAG = "RouteSelectionScreen"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}
