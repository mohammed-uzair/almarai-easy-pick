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
import com.almarai.business.Utils.Util.STATISTICS_CONSTRAINT_BEGINNING_DATE
import com.almarai.business.Utils.Util.STATISTICS_CONSTRAINT_INITIAL_DAYS_PERIOD
import com.almarai.data.easy_pick_models.Result
import com.almarai.data.easy_pick_models.Statistics
import com.almarai.data.easy_pick_models.StatisticsData
import com.almarai.data.easy_pick_models.util.exhaustive
import com.almarai.easypick.R
import com.almarai.easypick.databinding.ScreenStatisticsBinding
import com.almarai.easypick.extensions.Alert
import com.almarai.easypick.extensions.hideViewStateAlert
import com.almarai.easypick.extensions.showViewStateAlert
import com.almarai.easypick.utils.AppDateTimeFormat
import com.almarai.easypick.utils.DateUtil
import com.almarai.easypick.view_models.StatisticsViewModel
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.android.material.datepicker.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class StatisticsScreen : Fragment(R.layout.screen_statistics) {
    private val viewModel: StatisticsViewModel by viewModel()
    private lateinit var navController: NavController
    private val calendar = Calendar.getInstance()
    private lateinit var screenStatisticsBinding: ScreenStatisticsBinding
    private var startDate: String = ""
    private var endDate: String = ""

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

        val startDate = DateUtil.getDate(
            DateUtil.getPastDate(STATISTICS_CONSTRAINT_INITIAL_DAYS_PERIOD),
            AppDateTimeFormat.formatDDMMYYYY
        )
        val endDate = DateUtil.getDate(calendar.timeInMillis, AppDateTimeFormat.formatDDMMYYYY)
        screenStatisticsBinding.screenStatisticsDateRangeText.setText("$startDate - $endDate")

        animateUI()

        viewModel.statistics.observe(viewLifecycleOwner, Observer {
            it?.let { result ->
                when (result) {
                    is Result.Fetching -> showViewStateAlert(Alert.Loading)
                    is Result.Success -> showDataUi(result.data)
                    is Result.Error -> showViewStateAlert(Alert.Error)
                }.exhaustive
            }
        })

        screenStatisticsBinding.screenStatisticsDateRangeText.setOnClickListener {
            showDateRangePicker()
        }

        viewModel.getStatistics(startDate, endDate)
    }

    private fun showDataUi(statistics: Statistics) {
        //Hide the alert
        hideViewStateAlert()

        val commaAddedPagesCount =
            NumberFormat.getNumberInstance(Locale.US).format(statistics.numberOfPhysicalPapersSaved)

        viewModel.physicalPagesSaved.value = commaAddedPagesCount

        setChartAttributes(statistics.statisticsData)
    }

    private fun setChartAttributes(listOfEntries: List<StatisticsData>) {
        screenStatisticsBinding.screenStatisticsChart.invalidate()

        screenStatisticsBinding.screenStatisticsChart.isDragEnabled = true
        screenStatisticsBinding.screenStatisticsChart.setScaleEnabled(true)

        val entries = ArrayList<Entry>()
        for (entry in listOfEntries) {
            entries.add(Entry(entry.date, entry.papersSaved.toFloat()))
        }

        val lineDataSet = LineDataSet(entries, "")
        lineDataSet.color = ContextCompat.getColor(requireContext(), android.R.color.white)
        lineDataSet.setDrawCircles(false)
        lineDataSet.setDrawValues(false)
        lineDataSet.lineWidth = 4f
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
//        lineDataSet.setDrawFilled(true)
//        lineDataSet.fillColor = (ContextCompat.getColor(requireContext(), android.R.color.white))
//        lineDataSet.fillAlpha = 50

        val dataSets: ArrayList<ILineDataSet> = ArrayList()
        dataSets.add(lineDataSet)

        val lineData = LineData(dataSets)

        screenStatisticsBinding.screenStatisticsChart.data = lineData
        val legends = screenStatisticsBinding.screenStatisticsChart.legend
        legends.isEnabled = false
        screenStatisticsBinding.screenStatisticsChart.xAxis.valueFormatter = MyXAxisFormatter()
        screenStatisticsBinding.screenStatisticsChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        screenStatisticsBinding.screenStatisticsChart.xAxis.textColor =
            ContextCompat.getColor(requireContext(), android.R.color.white)
        screenStatisticsBinding.screenStatisticsChart.xAxis.setDrawLabels(true)
        screenStatisticsBinding.screenStatisticsChart.axisLeft.axisLineColor =
            ContextCompat.getColor(requireContext(), android.R.color.white)
        screenStatisticsBinding.screenStatisticsChart.axisLeft.textColor =
            ContextCompat.getColor(requireContext(), android.R.color.white)
        screenStatisticsBinding.screenStatisticsChart.axisLeft.setDrawGridLines(false)
        screenStatisticsBinding.screenStatisticsChart.description.isEnabled = false
        screenStatisticsBinding.screenStatisticsChart.setHardwareAccelerationEnabled(true)

        //Get the Theme specific color
        val typedValue = TypedValue()
        val theme = requireContext().theme
        theme.resolveAttribute(R.attr.colorBackgroundScreenHeader, typedValue, true)
        @ColorInt val color = typedValue.data

        screenStatisticsBinding.screenStatisticsChart.setBackgroundColor(color)
        screenStatisticsBinding.screenStatisticsChart.axisRight.isEnabled = false
//        screen_statistics_chart.animateX(4000, Easing.EaseInSine)
    }

    private fun showDateRangePicker() {
        //Set dialog theme, half screen popup
        val materialDatePickerBuilder = MaterialDatePicker.Builder.dateRangePicker().apply {
            setTheme(R.style.ThemeMaterialCalendar)
        }

        val constraintsBuilder = CalendarConstraints.Builder()
        val validators: ArrayList<CalendarConstraints.DateValidator> = ArrayList()

        if (startDate.isEmpty()) {
            startDate = DateUtil.getDate(
                DateUtil.getPastDate(
                    STATISTICS_CONSTRAINT_INITIAL_DAYS_PERIOD
                ), AppDateTimeFormat.formatDDMMYYYY
            )

            endDate = DateUtil.getCurrentDate(AppDateTimeFormat.formatDDMMYYYY)
        }

        materialDatePickerBuilder.setSelection(
            androidx.core.util.Pair(
                DateUtil.getDate(startDate, AppDateTimeFormat.formatDDMMYYYY),
                DateUtil.getDate(endDate, AppDateTimeFormat.formatDDMMYYYY)
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
            startDate = DateUtil.getDate(it.first, AppDateTimeFormat.formatDDMMYYYY)
            endDate = DateUtil.getDate(it.second, AppDateTimeFormat.formatDDMMYYYY)
            screenStatisticsBinding.screenStatisticsDateRangeText.setText("$startDate - $endDate")

            viewModel.getStatistics(startDate, endDate)
        }
    }

    private fun animateUI() {
        val topToBottom = AnimationUtils.loadAnimation(activity, R.anim.top_to_bottom)
        val bottomToTop = AnimationUtils.loadAnimation(activity, R.anim.bottom_card_up)

        screenStatisticsBinding.screenStatisticsChartLayout.startAnimation(bottomToTop)
        screenStatisticsBinding.screenStatisticsBackgroundImage.startAnimation(topToBottom)
        screenStatisticsBinding.screenStatisticsPhysicalPagesSavedText.startAnimation(topToBottom)
        screenStatisticsBinding.screenStatisticsPhysicalPagesSavedTextSummaryText.startAnimation(
            topToBottom
        )
    }

    class MyXAxisFormatter : ValueFormatter() {
        override fun getAxisLabel(value: Float, axis: AxisBase?) =
            DateUtil.getDate(value.toLong(), AppDateTimeFormat.formatDDMMYYYY)
    }
}