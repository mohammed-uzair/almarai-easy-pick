package com.almarai.easypick.utils

import android.text.Editable
import android.text.TextWatcher
import java.util.regex.Pattern

private const val zeroTo255 = ("(\\d{1,2}|(0|1)\\"
        + "d{2}|2[0-4]\\d|25[0-5])")

// Regex for a digit from 0 to 255 and
// followed by a dot, repeat 4 times.
// this is the regex to validate an IP address.
const val IP_ADDRESS_REGEX = (zeroTo255 + "\\."
        + zeroTo255 + "\\."
        + zeroTo255 + "\\."
        + zeroTo255)

class RegexMaskTextWatcher(regexForInputToMatch: String) : TextWatcher {

    private val regex = Pattern.compile(regexForInputToMatch)
    private var previousText: String = ""

    override fun afterTextChanged(s: Editable) {
        if (regex.matcher(s).matches()) {
            previousText = s.toString();
        } else {
            s.replace(0, s.length, previousText);
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

}