package com.ieugene.kotlinlab.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay
import timber.log.Timber
import kotlin.Exception

class RefreshDataWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        Timber.d("${Thread.currentThread().name} worker start downloading")
        try {
            delay(3000)
        } catch (e: Exception) {
            e.printStackTrace()
            Timber.d("worker error & retry")
            return Result.retry()
        }
        Timber.d("worker end downloading")
        return Result.success()
    }

    companion object {
        const val WORK_NAME = "com.ieugene.kotlinlab.workmanager.RefreshDataWorker"
    }

}