package com.pepsidrc.managers
import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.pepsidrc.utils.LogUtils

abstract class ActivityLifecycleManager : Application.ActivityLifecycleCallbacks {

    private val activity: Activity? = null
    var currentActivity: String? = null
    var activityCount: Int = 0
    var resumedActivityCount: Int = 0

    override fun onActivityCreated(p0: Activity, savedInstanceState: Bundle?) {
        activityCount++
    }

    override fun onActivityStarted(p0: Activity) {

    }

    override fun onActivityResumed(p0: Activity) {
        resumedActivityCount++

        currentActivity = null

        if (activity != null) {
            currentActivity = activity::class.java.name

            LogUtils.d("ActivityLifecycleManager",
                    "current = $currentActivity & resumed count = $resumedActivityCount & total count = $activityCount")
        }
    }

    override fun onActivityPaused(p0: Activity) {
        resumedActivityCount--
    }

    override fun onActivityStopped(p0: Activity) {

    }

    override fun onActivityDestroyed(p0: Activity) {
        activityCount--
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
        // nothing
    }
}