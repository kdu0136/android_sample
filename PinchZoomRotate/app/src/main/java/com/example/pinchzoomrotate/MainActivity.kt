package com.example.pinchzoomrotate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import com.example.pinchzoomrotate.ui.theme.PinchZoomRotateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PinchZoomRotateTheme {
                var scale by remember {
                    mutableFloatStateOf(1f)
                }
                var offset by remember {
                    mutableStateOf(Offset.Zero)
                }

                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1280f / 959f)
                ) {
                    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->

                        println("zoomChange: $zoomChange")
                        println("offsetChange: $offsetChange")

                        scale = (scale * zoomChange).coerceIn(1f, 5f)

                        val extraWidth = (scale - 1) * constraints.maxWidth
                        val extraHeight = (scale - 1) * constraints.maxHeight

                        val maxX = extraWidth / 2
                        val maxY = extraHeight / 2

                        offset = Offset(
                            x = (offset.x + scale * offsetChange.x).coerceIn(-maxX, maxX),
                            y = (offset.y + scale * offsetChange.y).coerceIn(-maxY, maxY)
                        )
                    }
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
//                            .pointerInput(Unit) {
//                                detectTransformGestures { centroid, offsetChange, zoom, _ ->
//                                    scale = (scale * zoom)//.coerceIn(1f, 5f)
//
//                                    val extraWidth = (scale - 1) * constraints.maxWidth
//                                    val extraHeight = (scale - 1) * constraints.maxHeight
//
//                                    val maxX = extraWidth / 2
//                                    val maxY = extraHeight / 2
//
//                                    println("centroid: $centroid")
//                                    println("offsetChange: $offsetChange")
//                                    println("zoom: $zoom")
//
//
////                                    offset = Offset(
////                                        x = (offset.x + scale * offsetChange.x).coerceIn(-maxX, maxX),
////                                        y = (offset.y + scale * offsetChange.y).coerceIn(-maxY, maxY)
////                                    )
//
//                                    offset += Offset(
//                                        x = (1 - zoom) * (centroid.x - offset.x - extraWidth),//.coerceIn(-maxX, maxX),
//                                        y = (1 - zoom) * (centroid.y - offset.y - extraHeight),//.coerceIn(-maxY, maxY)
//                                    ) + offsetChange
//                                }
//                            }
                            .transformable(state)
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                                translationX = offset.x
                                translationY = offset.y
                            },
                        painter = painterResource(id = R.drawable.kermit),
                        contentDescription = null
                    )
                }
            }
        }
    }
}
