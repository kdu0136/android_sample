package com.example.test_app.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.delay

class CustomWorker2(
    appContext: Context,
    params: WorkerParameters
): CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        var count = inputData.getInt("count", 0)
        while (count < 20) {
            count += 1
            println("CustomWorker2 count: $count")
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