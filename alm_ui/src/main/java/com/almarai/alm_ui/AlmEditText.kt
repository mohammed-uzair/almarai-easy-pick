package com.almarai.alm_ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.EditText
import com.almarai.alm_ui.Constants.CHANGED_BY_PROG
import com.almarai.alm_ui.Utils.APP_LOCALE
import java.util.*


/**
 * This class is the common class for edit text with no keyboard
 */
@SuppressLint("AppCompatCustomView")
class AlmEditText : EditText {
    //Global Variables
    private var type = 0

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        val a =
            context.theme.obtainStyledAttributes(attrs, R.styleable.AlmEditText, 0, 0)
        try {
            type = a.getInt(R.styleable.AlmEditText_type, 0)
            when (type) {
                StyleAttributesType.DEFAULT -> {
                    this.inputType = InputType.TYPE_CLASS_TEXT
                }
                StyleAttributesType.TEXT_TYPE_ALPHANUMERIC -> {
                    setAlphanumericProperties()
                }
                StyleAttributesType.TEXT_TYPE_NUMBER -> {
                    setNumericProperties()
                }
                StyleAttributesType.TEXT_TYPE_NUMBER_POSITIVE_ONLY -> {
                    setPositiveNumericProperties()
                }
                StyleAttributesType.TEXT_TYPE_AMOUNT -> {
                    setAmountProperties()
                }
                StyleAttributesType.TEXT_TYPE_AMOUNT_POSITIVE_ONLY -> {
                    setPositiveAmountProperties()
                }
                StyleAttributesType.TEXT_TYPE_RECYCLER_GRID -> {
                    setRecyclerViewGridProperties()
                }
                StyleAttributesType.TEXT_TYPE_QUANTITY -> {
                    setQuantityProperties()
                }
                StyleAttributesType.TEXT_TYPE_QUANTITY_POSITIVE_ONLY -> {
                    setPositiveQuantityProperties()
                }
            }
        } finally {
            a.recycle()
        }
        init()
    }

    private fun setAlphanumericProperties() {
        //Restrict the user to use only ALM_Alphanumeric digits
        this.keyListener =
            DigitsKeyListener.getInstance(resources.getString(R.string.alm_alphanumeric))
        this.filters = arrayOf<InputFilter>(AlmInputAlphaNumericFilter())
    }

    private fun setNumericProperties() {
        //Restrict the user to use only numbers without any other digits
        this.keyListener = DigitsKeyListener.getInstance(resources.getString(R.string.alm_number))

        //Set Length filter. Restricting to {@link AlmEditText#MAX_INPUT_DIGITS} characters only
        val lengthFilter = LengthFilter(MAX_INPUT_DIGITS)
        this.filters = arrayOf(AlmInputNumberFilter(this), lengthFilter)
    }

    private fun setPositiveNumericProperties() {
        //Restrict the user to use only numbers without any other digits
        this.keyListener =
            DigitsKeyListener.getInstance(resources.getString(R.string.alm_number_positive))

        //Set Length filter. Restricting to {@link AlmEditText#MAX_INPUT_DIGITS} characters only
        val lengthFilter = LengthFilter(MAX_INPUT_DIGITS)
        this.filters = arrayOf(AlmInputNumberFilter(this), lengthFilter)
    }

    private fun setAmountProperties() {
        this.inputType = InputType.TYPE_CLASS_NUMBER //This is a number value
        this.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL //This text supports decimal value
        this.keyListener =
            getDigitsKeyListener()//Should support minus sign, and this text may be a decimal
        this.filters = arrayOf<InputFilter>(
            AlmInputAmountFilter(
                this,
                Constants.COUNTRY_CURRENCY_MAX_DIGITS_BEFORE_DECIMAL.toInt(),
                Constants.COUNTRY_CURRENCY_MAX_DIGITS_AFTER_DECIMAL.toInt()
            )
        )
    }

    private fun setPositiveAmountProperties() {
        setAmountProperties()
    }

    private fun getDigitsKeyListener(): DigitsKeyListener {
        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            DigitsKeyListener.getInstance(APP_LOCALE, true, true)
        } else {
            DigitsKeyListener.getInstance(true, true)
        }
    }

    private fun setRecyclerViewGridProperties() {
        //Restrict the user to use only numeric digits
        this.keyListener = DigitsKeyListener.getInstance(resources.getString(R.string.alm_number))

        //Set Length filter. Restricting to {@link AlmEditText#MAX_INPUT_DIGITS} characters only
        val lengthFilter = LengthFilter(MAX_INPUT_DIGITS)
        this.filters = arrayOf<InputFilter>(lengthFilter)
    }

    private fun setQuantityProperties() {
        //Restrict the user to use only numbers without any other digits
        this.keyListener = DigitsKeyListener.getInstance(resources.getString(R.string.alm_number))

        //Set Length filter. Restricting to {@link AlmEditText#MAX_INPUT_DIGITS} characters only
        val lengthFilter =
            LengthFilter(MAX_INPUT_QUANTITY_DIGITS)
        this.filters = arrayOf(AlmInputNumberFilter(this), lengthFilter)
    }

    private fun setPositiveQuantityProperties() {
        //Restrict the user to use only numbers without any other digits
        this.keyListener =
            DigitsKeyListener.getInstance(resources.getString(R.string.alm_number_positive))

        //Set Length filter. Restricting to {@link AlmEditText#MAX_INPUT_DIGITS} characters only
        val lengthFilter =
            LengthFilter(MAX_INPUT_QUANTITY_DIGITS)
        this.filters = arrayOf(AlmInputNumberFilter(this), lengthFilter)
    }

    private fun init() {
        /*Don't allow the soft keyboard to show, if there is a hardware keyboard available*/
        this.showSoftInputOnFocus = !Constants.IS_HARDWARE_KEYBOARD_AVAILABLE

        //Add focus listener, we need to do some after focus change validations, as setting the decimals after the decimal point
        onFocusChangeListener = OnFocusChangeListener { v: View, hasFocus: Boolean ->
            //Check if the edit text type is amount or amount positive only
            if ((type == StyleAttributesType.TEXT_TYPE_AMOUNT || type == StyleAttributesType.TEXT_TYPE_AMOUNT_POSITIVE_ONLY) && !hasFocus) {
                //if the view leaves the focus
                validateWhenFocusIsGone(v)
            }
        }
    }

    private fun validateWhenFocusIsGone(view: View) {
        val editText = view as EditText

        //Get the amount text string value.
        val editTextValue = editText.text.toString()

        //Put the cursor at the last position
        editText.setSelection(editTextValue.length)

        //Check if the amount edit text does not have decimal and total length is greater than max allowed, if so add decimal point after max allowed digits
        if (!editTextValue.contains(".") && editTextValue.length > Constants.COUNTRY_CURRENCY_MAX_DIGITS_BEFORE_DECIMAL) {
            noDecimalWithExceedingMaxDigits(editText, editTextValue)
        } else if (!editTextValue.contains(".") && editTextValue.length <= Constants.COUNTRY_CURRENCY_MAX_DIGITS_BEFORE_DECIMAL) {
            //Append a decimal point at the end
            editText.append(".")

            //Add max number of zeros after the amount
            for (i in 0 until Constants.COUNTRY_CURRENCY_MAX_DIGITS_AFTER_DECIMAL) {
                editText.append("0")
            }
        } else if (editTextValue.contains(".")) {
            validateForDecimalPoint(editTextValue)
        }
    }

    private fun validateForDecimalPoint(editTextValue: String) {
        //Get the text after the decimal point.
        val afterDecimalText =
            editTextValue.substring(editTextValue.indexOf('.') + 1, editTextValue.length)

        //If there is only decimal point with no numbers then add the max number of zeros at end.
        if (afterDecimalText.isEmpty()) {
            for (i in 0 until Constants.COUNTRY_CURRENCY_MAX_DIGITS_AFTER_DECIMAL) {
                this@AlmEditText.append("0")
            }
        } else if (afterDecimalText.length < Constants.COUNTRY_CURRENCY_MAX_DIGITS_AFTER_DECIMAL) {
            for (i in 0 until Constants.COUNTRY_CURRENCY_MAX_DIGITS_AFTER_DECIMAL - afterDecimalText.length) {
                this@AlmEditText.append("0")
            }
        }
    }

    /**
     * Check if the amount edit text does not have decimal and total length is greater than max allowed, if so add decimal point after max allowed digits
     *
     * @param editText      the edit text that needs to be validated.
     * @param editTextValue the value of the edit text
     */
    private fun noDecimalWithExceedingMaxDigits(
        editText: EditText,
        editTextValue: String
    ) {
        val afterDecimalString: String

        //Get the structured string, also check for positive and negative signs.
        val stringBuilder: String = if (editTextValue.startsWith("-")) editTextValue.substring(
            0,
            Constants.COUNTRY_CURRENCY_MAX_DIGITS_BEFORE_DECIMAL + 1
        ) + "." +
                editTextValue.substring(
                    Constants.COUNTRY_CURRENCY_MAX_DIGITS_BEFORE_DECIMAL + 1,
                    editTextValue.length
                ) else editTextValue.substring(
            0,
            Constants.COUNTRY_CURRENCY_MAX_DIGITS_BEFORE_DECIMAL.toInt()
        ) + "." +
                editTextValue.substring(
                    Constants.COUNTRY_CURRENCY_MAX_DIGITS_BEFORE_DECIMAL.toInt(),
                    editTextValue.length
                )

        //Say the filter that the upcoming value is changed by program, and thus don't filter these values.
        CHANGED_BY_PROG = true

        //Set the total formatted value to the edit amount text.
        editText.setText(stringBuilder)

        //Get the structured string, also check for positive and negative signs.
        afterDecimalString =
            stringBuilder.substring(stringBuilder.indexOf(".") + 1, editText.text.length)

        //If the string does not complete with proper decimals at the end, add it.
        if (afterDecimalString.length < Constants.COUNTRY_CURRENCY_MAX_DIGITS_AFTER_DECIMAL) {
            for (i in 0 until Constants.COUNTRY_CURRENCY_MAX_DIGITS_AFTER_DECIMAL - afterDecimalString.length) {
                //Say the filter that the upcoming value is changed by program.
                CHANGED_BY_PROG = true

                //Now change the value
                editText.append("0")
            }
        }

        //Reset the flag
        CHANGED_BY_PROG = false
    }

    companion object {
        //Should not exceed more than 10, as it will cross the JAVA int range
        const val MAX_INPUT_DIGITS = 9
        const val MAX_INPUT_QUANTITY_DIGITS = 4
    }
}