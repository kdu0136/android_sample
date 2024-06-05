package com.example.test_app.worker

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

@SuppressLint("UnrememberedMutableState")
@Composable
fun WorkerScreen(
    context: Context
) {
    val customRequest = OneTimeWorkRequestBuilder<CustomWorker>()
        .setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        )
        .build()
    val custom2Request = OneTimeWorkRequestBuilder<CustomWorker2>()
        .build()
    val workManager = WorkManager.getInstance(context)

    val workInfos = workManager
        .getWorkInfosForUniqueWorkLiveData("customWork")
        .observeAsState()
        .value
    val customInfo = remember(workInfos) {
        workInfos?.find { it.id == customRequest.id }
    }
    val custom2Info = remember(workInfos) {
        workInfos?.find { it.id == custom2Request.id }
    }

    val count = derivedStateOf {
        val work1 = customInfo?.progress?.getInt("count", 0) ?: 0
        val work2 = custom2Info?.progress?.getInt("count", 0) ?: 0
        work1 + work2
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Button(onClick = {
            workManager
                .beginUniqueWork(
                    "customWork",
                    ExistingWorkPolicy.KEEP,
                    customRequest
                )
                .then(custom2Request)
                .enqueue()
        }) {
            Text(text = "Click", fontSize = 20.sp)
        }

        Text(
            text = count.value.toString(),
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 20.dp)
        )
    }
}