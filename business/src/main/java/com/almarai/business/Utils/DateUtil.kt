package com.almarai.business.Utils

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

object DateUtil {
    var APP_LOCALE: Locale = Locale.US

    /**
     * Function to get current date in default format (MM/dd/yy HH:mm:ss)
     *
     * @return Formatted date string
     */
    fun getCurrentDate(): String? {
        val dateFormat: DateFormat = SimpleDateFormat(
            AppDateTimeFormat.formatDDMMYYYYHHMMSS,
            Locale.ENGLISH
        )
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        return dateFormat.format(calendar.time)
    }

    /**
     * Function to get current date in default format (MM/dd/yy HH:mm)
     *
     * @return Formatted date string
     */
    fun getUTCCurrentDate(): String? {
        val dateFormat: DateFormat = SimpleDateFormat(
            AppDateTimeFormat.formatDDMMYYYYTHHMMSS,
            Locale.ENGLISH
        )
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        return dateFormat.format(calendar.time)
    }

    /**
     * Function to get current date in default format (MM/dd/yy HH:mm)
     *
     * @return Formatted date string
     */
    fun getCurrentDate(@AppDateTimeFormat format: String): String {
        val dateFormat: DateFormat =
            SimpleDateFormat(format, Locale.ENGLISH)
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        return dateFormat.format(calendar.time)
    }

    /**
     * Function to get current date in default format (MM/dd/yy HH:mm)
     *
     * @return Formatted date string
     */
    fun getCurrentDateInMillis() = Calendar.getInstance(APP_LOCALE).timeInMillis

    fun getDate(millis: Long?, @AppDateTimeFormat format: String): String {
        val dateFormat: DateFormat =
            SimpleDateFormat(format, APP_LOCALE)
        val calendar = Calendar.getInstance(APP_LOCALE)
        calendar.timeInMillis = millis ?: 0

        return dateFormat.format(calendar.time)
    }

    fun getDate(date: String, @AppDateTimeFormat format: String): Long {
        val sdf = SimpleDateFormat(format, APP_LOCALE)
        val result = sdf.parse(date)

        return result.time
    }

    /**
     * Function to format date string
     *
     * @param strDate    String date to format
     * @param fromFormat Date pattern
     * @param toFormat   Date pattern
     * @return Formatted date string
     */
    fun formatDate(
        strDate: String?,
        fromFormat: String?,
        toFormat: String?
    ): String? {
        var date: Date? = null
        var formattedDate = ""
        try {
            date = SimpleDateFormat(fromFormat, APP_LOCALE).parse(strDate)
            val simpleDateFormat =
                SimpleDateFormat(toFormat, APP_LOCALE)
            formattedDate = simpleDateFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return formattedDate
    }

    public fun convertDateToMilliseconds(
        date: String?,
        dateFormat: String?
    ): Long {
        val format = SimpleDateFormat(dateFormat)
        try {
            val d = format.parse(date)
            return d.time
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return 0
    }

    fun validateSalesDate(salesDate: String): Boolean {
        val differenceInDays = 2

        //Get current system date
        val currentDeviceDate = Calendar.getInstance()

        //Set the start date
        val startSalesDate = Calendar.getInstance()

        //Set the end date
        val endSalesDate = Calendar.getInstance()
        val simpleDateFormat =
            SimpleDateFormat(AppDateTimeFormat.formatDDMMYYYY, APP_LOCALE)
        return try {
            startSalesDate.time = simpleDateFormat.parse(salesDate)
            startSalesDate.add(Calendar.DATE, -differenceInDays)
            endSalesDate.time = simpleDateFormat.parse(salesDate)
            endSalesDate.add(Calendar.DATE, differenceInDays + 1)

            //Check if the current device date falls within the start and the end sales date
            return currentDeviceDate.after(startSalesDate) && currentDeviceDate.before(endSalesDate)
        } catch (e: ParseException) {
            e.printStackTrace()
            false
        }
    }

    fun checkForDateDifference(
        firstDate: String?,
        secondDate: String?
    ): Long {
        val simpleDateFormat = SimpleDateFormat(
            AppDateTimeFormat.formatDDMMYYYYHHMMSS,
            APP_LOCALE
        )
        val difference: Long
        return try {
            val firstDateObject = simpleDateFormat.parse(firstDate)
            val secondDateObject = simpleDateFormat.parse(secondDate)

            //Check if the current device date falls within the start and the end sales date
            val diffInMillis =
                abs(secondDateObject.time - firstDateObject.time)
            difference = TimeUnit.DAYS.convert(
                diffInMillis,
                TimeUnit.MILLISECONDS
            )
            difference
        } catch (e: ParseException) {
            e.printStackTrace()
            0
        }
    }

    fun getDateStringFromLong(dateFormat: String?, currentTimeMillis: Long) =
        SimpleDateFormat(dateFormat, Locale.getDefault()).format(currentTimeMillis)

    fun getFutureDate(daysFromToday: Int): Long {
        return getVarianceDate(daysFromToday)
    }

    fun getPastDate(daysFromToday: Int): Long {
        return getVarianceDate(-daysFromToday)
    }

    private fun getVarianceDate(daysFromToday: Int): Long {
        val calender = Calendar.getInstance(APP_LOCALE)
        calender.add(Calendar.DATE, daysFromToday)

        return calender.timeInMillis
    }

    fun getDaysMonthsYears() {
        val startDate: Calendar = GregorianCalendar(2019, Calendar.MAY, 19)
        val endDate: Calendar = GregorianCalendar()
        endDate.time = Date()

        val yearsInBetween = endDate[Calendar.YEAR] - startDate[Calendar.YEAR]
        val monthsDiff = endDate[Calendar.MONTH] - startDate[Calendar.MONTH]

        val months = yearsInBetween * 12 + monthsDiff.toLong()
        val years = yearsInBetween.toLong()
    }
}