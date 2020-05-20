package com.almarai.easypick.listeners.interfaces

import androidx.fragment.app.Fragment

interface IFragment {
    fun activityCreated(fragment: Fragment)
    fun activityResumed(fragment: Fragment)
    fun activityPaused(fragment: Fragment)
    fun activityDestroyed(fragment: Fragment)
}