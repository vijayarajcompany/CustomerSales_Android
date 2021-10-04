package com.pepsidrc.base

import android.os.Bundle
import androidx.multidex.MultiDexApplication
import com.facebook.drawee.backends.pipeline.Fresco
import com.pepsidrc.di.AppModule
import com.pepsidrc.di.NetworkModule
import com.pepsidrc.managers.ActivityLifecycleManager
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin


import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustConfig
import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import com.adjust.sdk.LogLevel


class BaseApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        val appToken = "yzvbnils8q2o"
        val environment = AdjustConfig.ENVIRONMENT_SANDBOX
        val config = AdjustConfig(this, appToken, environment)

        config.setPreinstallTrackingEnabled(true);
        config.setSendInBackground(true);
        config.setLogLevel(LogLevel.ERROR);

        Adjust.onCreate(config)
        registerActivityLifecycleCallbacks(AdjustLifecycleCallbacks())

        sInstance = this

        startKoin {
            androidContext(this@BaseApplication)
            logger(AndroidLogger())
            modules(listOf(AppModule.appModule, NetworkModule.networkModule))
        }
        Fresco.initialize(this)

    }

    class AdjustLifecycleCallbacks : ActivityLifecycleCallbacks {
        override fun onActivityCreated(p0: Activity, p1: Bundle?) {

        }

        override fun onActivityStarted(p0: Activity) {

        }

        override fun onActivityResumed(activity: Activity) {
            Adjust.onResume()
        }

        override fun onActivityPaused(activity: Activity) {
            Adjust.onPause()
        }

        override fun onActivityStopped(p0: Activity) {
            Adjust.onPause()
        }

        override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {

        }

        override fun onActivityDestroyed(p0: Activity) {

        }
    }

    companion object {

        private lateinit var sInstance: BaseApplication

        fun getInstance() = sInstance
    }
}

