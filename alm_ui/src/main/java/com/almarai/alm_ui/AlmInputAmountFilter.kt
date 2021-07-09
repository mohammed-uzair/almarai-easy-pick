package com.almarai.alm_ui

import android.text.InputFilter
import android.text.Spanned
import android.text.method.DigitsKeyListener
import android.widget.EditText
import com.almarai.alm_ui.Constants.CHANGED_BY_PROG

/**
 * This is the filter class for input type to support amount inputs, this class is also responsible
 * for county wise filtration.
 *
 *
 * NOTE
 * Dest is the text already entered in the text box and
 * Source is the currently entered text in text box
 *
 *
 * The value that you will return will be appended with the already typed value
 * EG:
 * Dest     Source      Return
 * ABC      DEF         ""(Empty String) => ABC
 * ABC      DEF         Source => ABCDEF
 */
class AlmInputAmountFilter //Set the gravity for the amount edit text to right
//        editText.setGravity(Gravity.RIGHT);
internal constructor(
    private val editText: EditText, private val maxDigitsBeforeDecimal: Int,
    private val maxDigitsAfterDecimal: Int
) : DigitsKeyListener(), InputFilter {
    override fun filter(
        source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int,
        dend: Int
    ): CharSequence {
        //Check if the source is not edited by user, that is its been set by program, if so don't filter any thing
        if (CHANGED_BY_PROG) {
            return ""
        }

        //Value to return
        var returnValue = source.toString()

        //If the whole text is selected and modified, allow it
        if (dstart + dend == dest.length) {
            return source
        }

        //Concatenate the edit text value with the currently typed digit
        val typedText = dest.toString() + source.toString()

        //If the text starts with "-.", make it "-0."
        if (dest.toString() == "-" && source == ".") {
            returnValue = "-0."
        } else if ((dest.toString() == "0" || dest.toString() == "-") && (source.toString()
                    == "0")
        ) {
            returnValue = ""
        } else if (dest.toString().isEmpty() && source == "-") {
            returnValue = "-"
        } else if (dest.toString().isEmpty() && source == ".") {
            returnValue = "0."
        } else if (!typedText.contains(".") && (!typedText.startsWith("-") && (typedText.length
                    > maxDigitsBeforeDecimal) || typedText.startsWith("-") && (typedText.length - 1
                    > maxDigitsBeforeDecimal))
        ) {
            returnValue = ""
        } else if (!dest.toString().contains(".") && source == ".") {
            returnValue = source.toString()
        } else if (typedText.contains(".") && typedText != "-0" && typedText != "-") {
            //Get the position where the dot is placed.
            val decimalPointPosition = typedText.indexOf(".")

            //Get the current cursor position.
            val cursorPosition = editText.selectionStart

            //Get a new string for before the decimal point
            val textBeforeDecimalPoint = typedText.substring(0, decimalPointPosition)

            //Get a new string for after the decimal point
            val textAfterDecimalPoint =
                typedText.substring(decimalPointPosition + 1, typedText.length)
            if (decimalPointPosition == dstart && source == "" && (textBeforeDecimalPoint.length
                        == maxDigitsBeforeDecimal && textAfterDecimalPoint.isNotEmpty())
            ) {
                returnValue = ""
            } else if (cursorPosition <= decimalPointPosition) {

                //Check if the digits before decimal point is less than or equal to the max allowed digits, also check the same for minus sign texts also
                returnValue =
                    if (typedText.startsWith("-")) if (textBeforeDecimalPoint.length - 1 < maxDigitsBeforeDecimal) source.toString() else "" else if (textBeforeDecimalPoint.length < maxDigitsBeforeDecimal) source.toString() else ""
            } else {
                //If the length of the text is after decimal point is greater that the max allowed then return empty.
                if (textAfterDecimalPoint.length > maxDigitsAfterDecimal) {
                    returnValue = ""
                }
            }
            return returnValue
        } else {
            //Return any other entered case, consider this as the default case
            return super.filter(source, start, end, dest, dstart, dend)
        }
        return returnValue
    }

}