package com.example.composeanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.composeanimation.ui.theme.ComposeAnimationTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeAnimationTheme {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    var isVisible by remember {
                        mutableStateOf(false)
                    }
                    var isRound by remember { mutableStateOf(false) }
                    Button(onClick = {
                        isVisible = !isVisible
                        isRound = !isRound
                    }) {
                        Text(text = "Toggle")
                    }
//                    val transition = updateTransition(
//                        targetState = isRound,
//                        label = "",
//                    )
//                    val borderRadius by transition.animateInt(
////                        transitionSpec = { tween(2000) },
//                        label = "borderRadius",
//                        targetValueByState = { if (it) 100 else 0 }
//                    )
//                    val color by transition.animateColor(
////                        transitionSpec = { tween(2000) },
//                        label = "color",
//                        targetValueByState = { if (it) Color.Red else Color.Green }
//                    )
//                    val borderRadius by animateIntAsState(
//                        targetValue = if (isRound) 100 else 0,
//                        animationSpec = tween(
//                            durationMillis = 500,
//                            easing = LinearEasing,
//                        ),
//                        label = "",
//                    )
//                    val transition = rememberInfiniteTransition(label = "")
//                    val color by transition.animateColor(
//                        initialValue = Color.Red,
//                        targetValue = Color.Green,
//                        animationSpec = infiniteRepeatable(
//                            animation = tween(
//                                durationMillis = 2000,
//                            ),
//                            repeatMode = RepeatMode.Reverse,
//                        ), label = ""
//                    )
//                    Box(
//                        modifier = Modifier
//                            .size(200.dp)
////                            .clip(RoundedCornerShape(borderRadius))
//                            .background(color)
//                    )

//                    AnimatedVisibility(
//                        visible = isVisible,
//                        enter = slideInHorizontally() + fadeIn(),
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .weight(1f),
//                    ) {
//                        Box(modifier = Modifier.background(Color.Red))
//                    }

                    AnimatedContent(
                        targetState = isVisible,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        content = {
                            if (it) {
                                Box(modifier = Modifier.background(Color.Red))
                            } else {
                                Box(modifier = Modifier.background(Color.Green))
                            }
                        },
                        transitionSpec = {
                            if (targetState) {
                                slideInHorizontally() togetherWith slideOutHorizontally(targetOffsetX = { it })
                            } else {
                                slideInHorizontally(initialOffsetX = { it }) togetherWith slideOutHorizontally()
                            }
                        }, label = ""
                    )
                }
            }
        }
    }
}
