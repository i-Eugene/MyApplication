package com.ieugene.kotlinlab.app

import android.app.Application
import android.util.Log
import com.ieugene.kotlinlab.BuildConfig
import timber.log.Timber

class KotlinApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(LogTree())
    }
}

class LogTree : Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (BuildConfig.DEBUG || Log.isLoggable("KotlinActivity", Log.DEBUG))
            super.log(priority, tag, message, t)
    }
}
