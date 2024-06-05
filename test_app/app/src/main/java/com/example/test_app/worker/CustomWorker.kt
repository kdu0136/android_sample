package com.example.test_app.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.delay

class CustomWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        var count = 0
        while (count < 10) {
            count += 1
            println("CustomWorker count: $count")
            setProgress(workDataOf("count" to count))
            delay(500)
        }

        return Result.success(
            workDataOf(
                "count" to count
            )
        )
    }
}