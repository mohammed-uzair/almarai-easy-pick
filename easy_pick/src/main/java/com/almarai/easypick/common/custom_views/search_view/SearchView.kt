package com.almarai.easypick.common.custom_views.search_view

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.FrameLayout
import com.almarai.easypick.R
import kotlinx.android.synthetic.main.view_search.view.*

class SearchView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    var onBarcodeScanClickListener: OnBarcodeScanClickListener? = null
    var onSpeechToTextClickListener: OnSpeechToTextClickListener? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int?) : this(context, attrs)

    init {
        LayoutInflater.from(context).inflate(R.layout.view_search, this, true)

        open_search_button.setOnClickListener { openSearch() }
//        openSearch()
//        close_search_button.setOnClickListener { closeSearch() }
        execute_barcode_scan_button.setOnClickListener {
            onBarcodeScanClickListener?.onClick()
        }

        execute_voice_button.setOnClickListener {
            onSpeechToTextClickListener?.onClick()
        }
    }

    fun openSearch() {
        search_input_text.setText("")
        search_open_view.visibility = View.VISIBLE
        search_input_text.requestFocus()
//        val circularReveal = ViewAnimationUtils.createCircularReveal(
//            search_open_view,
//            (open_search_button.right + open_search_button.left) / 2,
//            (open_search_button.top + open_search_button.bottom) / 2,
//            0f, width.toFloat()
//        )
//        circularReveal.duration = 300
//        circularReveal.start()
    }

    private fun closeSearch() {
        val circularConceal = ViewAnimationUtils.createCircularReveal(
            search_open_view,
            (open_search_button.right + open_search_button.left) / 2,
            (open_search_button.top + open_search_button.bottom) / 2,
            width.toFloat(), 0f
        )

        circularConceal.duration = 300
        circularConceal.start()
        circularConceal.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) = Unit
            override fun onAnimationCancel(animation: Animator?) = Unit
            override fun onAnimationStart(animation: Animator?) = Unit
            override fun onAnimationEnd(animation: Animator?) {
                search_open_view.visibility = View.INVISIBLE
                search_input_text.setText("")
                circularConceal.removeAllListeners()
            }
        })
    }

    fun setText(text: String) {
        search_input_text.setText(text.trim())
        search_input_text.selectAll()
    }
}

interface OnBarcodeScanClickListener {
    fun onClick()
}

interface OnSpeechToTextClickListener {
    fun onClick()
}