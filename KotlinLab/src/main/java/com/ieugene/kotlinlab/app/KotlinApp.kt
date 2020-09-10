package com.ieugene.kotlinlab.app

import android.app.Application
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.ieugene.kotlinlab.BuildConfig
import com.ieugene.kotlinlab.workmanager.RefreshDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

class KotlinApp : Application() {
    override fun onCreate() {
        super.onCreate()
//        Timber.plant(LogTree())
        delayInit()
    }
}

private val applicationCoroutine = CoroutineScope(Dispatchers.Default)

private fun delayInit() {
    applicationCoroutine.launch {
        Timber.plant(Timber.DebugTree())
        setupRecurringWork()
    }
}

private fun setupRecurringWork() {
    //最小时间间隔是15分钟，源码限制
    val requestBuilder = PeriodicWorkRequestBuilder<RefreshDataWorker>(15, TimeUnit.MINUTES).build()
    WorkManager.getInstance().enqueueUniquePeriodicWork(RefreshDataWorker.WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, requestBuilder)
}

class LogTree : Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (BuildConfig.DEBUG || Log.isLoggable("KotlinActivity", Log.DEBUG))
            super.log(priority, tag, message, t)
    }
}
