package com.almarai.easypick.screens

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.almarai.business.Utils.AppDateTimeFormat
import com.almarai.business.Utils.DateUtil
import com.almarai.business.Utils.Util.STATISTICS_CONSTRAINT_BEGINNING_DATE
import com.almarai.business.Utils.Util.STATISTICS_CONSTRAINT_INITIAL_DAYS_PERIOD
import com.almarai.data.easy_pick_models.Result
import com.almarai.data.easy_pick_models.statistics.Statistics
import com.almarai.data.easy_pick_models.statistics.StatisticsData
import com.almarai.data.easy_pick_models.util.exhaustive
import com.almarai.easypick.R
import com.almarai.easypick.databinding.ScreenStatisticsBinding
import com.almarai.easypick.extensions.Alert
import com.almarai.easypick.extensions.hideViewStateAlert
import com.almarai.easypick.extensions.showViewStateAlert
import com.almarai.easypick.view_models.StatisticsViewModel
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.android.material.datepicker.*
import androidx.fragment.app.viewModels
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class StatisticsScreen : Fragment() {
    private val viewModel: StatisticsViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var screenStatisticsBinding: ScreenStatisticsBinding

    private var selectedChartType: ChartType = ChartType.Line
    private val calendar = Calendar.getInstance()
    private var startDate: Long = DateUtil.getPastDate(STATISTICS_CONSTRAINT_INITIAL_DAYS_PERIOD)
    private var endDate: Long = DateUtil.getCurrentDateInMillis()

    private lateinit var statisticsData: List<StatisticsData>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        screenStatisticsBinding =
            DataBindingUtil.inflate(inflater, R.layout.screen_statistics, container, false)
        screenStatisticsBinding.apply {
            lifecycleOwner = this@StatisticsScreen
            viewModel = this@StatisticsScreen.viewModel
        }

        return screenStatisticsBinding.root
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
        setDateInRangePicker()

        viewModel.statistics.observe(viewLifecycleOwner, Observer {
            it?.let { result ->
                when (result) {
                    is Result.Fetching -> showViewStateAlert(Alert.Loading)
                    is Result.Success -> showDataUi(result.data)
                    is Result.Error -> showViewStateAlert(Alert.Error, result.exceptionMessage)
                }.exhaustive
            }
        })

        screenStatisticsBinding.screenStatisticsDateRangeText.setOnClickListener {
            showDateRangePicker()
        }

        viewModel.getStatistics(startDate, endDate)

        screenStatisticsBinding.screenStatisticsPhysicalPagesSavedText.setOnClickListener {
//            selectedChartType = ChartType.Pie
            closeChart()

            // Make some wanted delay for the animations to complete
            Thread.sleep(500)
            openChart()
        }
    }

    private fun setDateInRangePicker() {
        viewModel.datePicker.value = "$startDate - $endDate"
    }

    private fun showDataUi(statistics: Statistics) {
        //Hide the alert
        hideViewStateAlert()

        val commaAddedPagesCount =
            NumberFormat.getNumberInstance(Locale.US).format(statistics.numberOfPhysicalPapersSaved)

        viewModel.physicalPagesSaved.value = commaAddedPagesCount

        statisticsData = statistics.statisticsData
        openChart()
    }

    private fun openChart() {
        when (selectedChartType) {
            is ChartType.Line -> setLineChartAttributes()
            is ChartType.Bar -> setBarChartAttributes()
            is ChartType.Pie -> setPieChartAttributes()
        }.exhaustive

        screenStatisticsBinding.screenStatisticsChart.visibility = View.VISIBLE

        //Animate and show the chart layout
        screenStatisticsBinding.screenStatisticsChart.startAnimation(
            AnimationUtils.loadAnimation(
                activity,
                R.anim.anim_card_bottom_to_top
            )
        )
    }

    private fun closeChart() {
//        CoroutineScope(Co).launch {
//
//        }
        //Close the chart in animation
        screenStatisticsBinding.screenStatisticsChart.startAnimation(
            AnimationUtils.loadAnimation(
                activity,
                R.anim.anim_card_top_to_bottom
            )
        )

        screenStatisticsBinding.screenStatisticsChart.visibility = View.GONE

        //Clear all the chart data
        screenStatisticsBinding.screenStatisticsChart.invalidate()
    }

    //region Charts Attributes
    private fun setLineChartAttributes() {
        //Get the Theme specific color
        val typedValue = TypedValue()
        val theme = requireContext().theme
        theme.resolveAttribute(R.attr.colorTitle, typedValue, true)
        @ColorInt val color = typedValue.data

        //Set the chart data
        val entries = ArrayList<Entry>()
        for (entry in statisticsData) {
            entries.add(Entry(entry.date, entry.papersSaved.toFloat()))
        }

        val lineDataSet = LineDataSet(entries, "")

        lineDataSet.setDrawCircles(false)
        lineDataSet.setDrawValues(false)

        lineDataSet.lineWidth = 4f
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        lineDataSet.color = color

        lineDataSet.setDrawFilled(true)
        lineDataSet.fillColor = (ContextCompat.getColor(requireContext(), android.R.color.white))
        lineDataSet.fillAlpha = 10

        val dataSets: ArrayList<ILineDataSet> = ArrayList()
        dataSets.add(lineDataSet)

        val lineData = LineData(dataSets)

        //Set all the chart UI changes
        //X-Axis label formatter
        screenStatisticsBinding.screenStatisticsChart.xAxis.valueFormatter =
            object : ValueFormatter() {
                override fun getAxisLabel(value: Float, axis: AxisBase?) =
                    DateUtil.getDate(value.toLong(), AppDateTimeFormat.formatDDMMYYYY)
            }

        screenStatisticsBinding.screenStatisticsChart.legend.isEnabled = false
        screenStatisticsBinding.screenStatisticsChart.isDragEnabled = true
        screenStatisticsBinding.screenStatisticsChart.setScaleEnabled(true)
        screenStatisticsBinding.screenStatisticsChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        screenStatisticsBinding.screenStatisticsChart.xAxis.textColor = color
        screenStatisticsBinding.screenStatisticsChart.xAxis.setLabelCount(4, true)
        screenStatisticsBinding.screenStatisticsChart.xAxis.setDrawLabels(true)
        screenStatisticsBinding.screenStatisticsChart.xAxis.setDrawGridLines(false)
        screenStatisticsBinding.screenStatisticsChart.axisLeft.axisLineColor = color
        screenStatisticsBinding.screenStatisticsChart.axisLeft.textColor = color
        screenStatisticsBinding.screenStatisticsChart.axisLeft.setDrawGridLines(false)
        screenStatisticsBinding.screenStatisticsChart.description.isEnabled = false
        screenStatisticsBinding.screenStatisticsChart.setHardwareAccelerationEnabled(true)
        screenStatisticsBinding.screenStatisticsChart.axisRight.isEnabled = false
        screenStatisticsBinding.screenStatisticsChart.setExtraOffsets(5f, 5f, 15f, 20f)

        //Set the data
        screenStatisticsBinding.screenStatisticsChart.data = lineData
    }

    private fun setBarChartAttributes() {}

    private fun setPieChartAttributes() {}
    //endregion

    private fun showDateRangePicker() {
        //Set dialog theme, half screen popup
        val materialDatePickerBuilder = MaterialDatePicker.Builder.dateRangePicker().apply {
            setTheme(R.style.ThemeMaterialCalendar)
        }

        val constraintsBuilder = CalendarConstraints.Builder()
        val validators: ArrayList<CalendarConstraints.DateValidator> = ArrayList()

        materialDatePickerBuilder.setSelection(
            androidx.core.util.Pair(
                startDate,
                endDate
            )
        )

        //Set Minimum start date from
        validators.add(DateValidatorPointForward.from(STATISTICS_CONSTRAINT_BEGINNING_DATE.timeInMillis))

        //Set maximum end date to (i:e Current date)
        validators.add(DateValidatorPointBackward.before(calendar.timeInMillis))

        constraintsBuilder.setValidator(CompositeDateValidator.allOf(validators))

        //Set minimum constraints months, year to show on calender
        constraintsBuilder.setStart(STATISTICS_CONSTRAINT_BEGINNING_DATE.timeInMillis)

        //Set minimum constraints months, year to show on calender
        constraintsBuilder.setEnd(calendar.timeInMillis)

        materialDatePickerBuilder.setCalendarConstraints(constraintsBuilder.build())

        val picker = materialDatePickerBuilder.build()
        picker.show(activity?.supportFragmentManager!!, picker.toString())

        picker.addOnPositiveButtonClickListener {
            startDate = it.first ?: startDate
            endDate = it.second ?: endDate

            setDateInRangePicker()

            viewModel.getStatistics(startDate, endDate)
        }
    }

    private fun animateUI() {
        val topToBottom = AnimationUtils.loadAnimation(activity, R.anim.anim_top_to_bottom)

        screenStatisticsBinding.screenStatisticsBackgroundImage.startAnimation(topToBottom)
        screenStatisticsBinding.screenStatisticsPhysicalPagesSavedText.startAnimation(topToBottom)
        screenStatisticsBinding.screenStatisticsPhysicalPagesSavedTextSummaryText.startAnimation(
            topToBottom
        )
    }

    sealed class ChartType {
        object Line : ChartType()
        object Bar : ChartType()
        object Pie : ChartType()
    }
}