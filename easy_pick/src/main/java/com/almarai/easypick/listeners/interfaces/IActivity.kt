package com.almarai.easypick.listeners.interfaces

import android.app.Activity

interface IActivity {
    fun activityCreated(activity: Activity)
    fun activityResumed(activity: Activity)
    fun activityPaused(activity: Activity)
    fun activityDestroyed(activity: Activity)
}