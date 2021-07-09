package com.almarai.alm_ui

import android.text.InputFilter
import android.text.Spanned
import android.text.method.DigitsKeyListener
import com.almarai.alm_ui.Constants.CHANGED_BY_PROG

/**
 * This is the filter class for input type to support alpha numeric inputs.
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
 * ABC      DEF         Source(Empty String) => ABCDEF
 */
class AlmInputAlphaNumericFilter : DigitsKeyListener(), InputFilter {
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

        //Value to return
        return source.toString()
    }
}