package com.almarai.alm_ui

import android.text.InputFilter
import android.text.Spanned
import android.text.method.DigitsKeyListener
import android.widget.EditText
import com.almarai.alm_ui.Constants.CHANGED_BY_PROG

/**
 * This is the filter class for input type to support number inputs.
 *
 *
 * NOTE
 * Dest :  is the text already entered in the text box and
 * Source : is the currently entered text in text box
 *
 *
 * The value that you will return will be appended with the already typed value
 * EG:
 * Dest     Source      Return
 * ABC      DEF         ""(Empty String) => ABC
 * ABC      DEF         Source(Empty String) => ABCDEF
 */
class AlmInputNumberFilter internal constructor(private val editText: EditText) :
    DigitsKeyListener(),
    InputFilter {
    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence {
        //Check if the source is not edited by user, that is its been set by program, if so don't filter any thing
        if (CHANGED_BY_PROG) {
            return ""
        }

        //Concatenate the edit text value with the currently typed digit
        val typedText = dest.toString() + source.toString()

        //Get the position where the dot is placed.
        val negativeSymbolPosition = typedText.indexOf("-")

        //Get the current cursor position.
        val cursorPosition = editText.selectionStart

        /*If user is entering any text replacing the whole destination text then allow the user
          i.e if the user has selected the whole text and now is changing the whole text*/if (dstart == 0 && dend == dest.toString().length) {
            return source
        }

        //Do not allow the user to enter 0 at the first position if already a value is entered
        if (cursorPosition == 0 && source == "0") return ""

        //Do not allow any values to be entered before the negative symbol
        if (negativeSymbolPosition > -1 && cursorPosition <= negativeSymbolPosition && dest.toString()
                .isNotEmpty()
        ) return ""

        //Do not allow 0 to be entered after the negative symbol
        if (negativeSymbolPosition > -1 && cursorPosition > negativeSymbolPosition && source.toString() == "0") return ""

        //Do not allow the user to enter only the zero digits
        return if (dest.toString() == "0" && (source.toString() == "0" || dest.toString() == "-")) "" else if (dest.toString()
                .contains("-") && source.toString()
                .contains("-")
        ) "" else return source
    }

}