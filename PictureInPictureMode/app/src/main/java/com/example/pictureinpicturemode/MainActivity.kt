package com.example.pictureinpicturemode

import android.app.PendingIntent
import android.app.PictureInPictureParams
import android.app.RemoteAction
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Rational
import android.widget.VideoView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toAndroidRectF
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.toRect
import com.example.pictureinpicturemode.ui.theme.PictureInPictureModeTheme

class MainActivity : ComponentActivity() {

    class MyReceiver: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            println("Clicked on PIP action")
        }
    }

    private val isPipSupported by lazy {
        packageManager.hasSystemFeature(
            PackageManager.FEATURE_PICTURE_IN_PICTURE
        )
    }

    private var videoRect = RectF()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PictureInPictureModeTheme {
                AndroidView(
                    factory = {
                        VideoView(it, null).apply {
                            setVideoURI(Uri.parse("android.resource://$packageName/${R.raw.sample}"))
                            start()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned {
                            videoRect = it.boundsInWindow().toAndroidRectF()
                        }
                )
            }
        }
    }

    private fun updatePipParams(): PictureInPictureParams? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PictureInPictureParams.Builder()
                .setSourceRectHint(videoRect.toRect())
                .setAspectRatio(Rational(16, 9))
                .setActions(
                    listOf(
                        RemoteAction(
                            Icon.createWithResource(
                                applicationContext,
                                R.drawable.baseline_baby_changing_station_24,
                            ),
                            "baby change",
                            "description",
                            PendingIntent.getBroadcast(
                                applicationContext,
                                0,
                                Intent(applicationContext, MyReceiver::class.java),
                                PendingIntent.FLAG_IMMUTABLE,
                            )
                        )
                    )
                )
//                .setAutoEnterEnabled(true)
//                .setSeamlessResizeEnabled(false)
                .build()
        } else null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        if (!isPipSupported) return

        updatePipParams()?.let { param ->
            enterPictureInPictureMode(param)
        }
    }
}
