package com.pepsidrc.utils

import android.util.Log
import com.pepsidrc.BuildConfig
import java.lang.Exception

object Logger {

    private const val TAG = "Logger"

    fun Debug(tag:String ?= TAG, msg:String){
        if (BuildConfig.DEBUG){
            Log.d(tag,msg)
        }
    }
    fun Debug(tag:String= TAG, msg:Exception?){
        if (BuildConfig.DEBUG){
            Log.d(tag,msg?.localizedMessage!!)
        }
    }

    fun Error(tag:String= TAG, msg:String){
        if (BuildConfig.DEBUG){
            Log.e(tag,msg)
        }
    }
    fun Error(tag:String= TAG, msg:Exception?){
        if (BuildConfig.DEBUG){
            Log.e(tag,msg?.localizedMessage!!)
        }
    }

}