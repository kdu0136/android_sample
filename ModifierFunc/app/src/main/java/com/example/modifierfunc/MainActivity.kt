package com.example.modifierfunc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.magnifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.example.modifierfunc.ui.theme.ModifierFuncTheme

@OptIn(ExperimentalFoundationApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ModifierFuncTheme {
                var offset by remember {
                    mutableStateOf(Offset.Zero)
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(true) {
                            detectDragGestures { change, _ ->
                                offset = change.position
                            }
                        }
                        .magnifier(
                            sourceCenter = {
                                offset
                            },
                            magnifierCenter = {
                                offset// - Offset(x = 0f, y = 200f)
                            },
                            size = DpSize(150.dp, 150.dp),
                            cornerRadius = 150.dp,
                            zoom = 2.5f,
                        ),
//                        .drawWithContent {
//                            drawContent()
//                            drawCircle(
//                                color = Color.Red,
//                                center = offset,
//                            )
//                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.FillWidth,
                        painter = painterResource(id = R.drawable.kermit),
                        contentDescription = null
                    )
//                    Text(
//                        modifier = Modifier
//                            .basicMarquee(
//                                iterations = Int.MAX_VALUE
//                            ),
//                        text = "long text sample. long text sample. long text sample. long text sample. long text sample. long text sample. long text sample. long text sample. long text sample. long text sample. long text sample. long text sample."
//                    )
                }
            }
        }
    }
}
