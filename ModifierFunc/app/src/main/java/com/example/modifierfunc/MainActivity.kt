package com.example.modifierfunc

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.content.MediaType
import androidx.compose.foundation.content.contentReceiver
import androidx.compose.foundation.content.hasMediaType
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.modifierfunc.ui.theme.ModifierFuncTheme

@OptIn(ExperimentalFoundationApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ModifierFuncTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        var text by remember {
                            mutableStateOf("")
                        }
                        var imageUri by remember {
                            mutableStateOf(Uri.EMPTY)
                        }

                        AsyncImage(
                            modifier = Modifier.height(150.dp),
                            model = imageUri,
                            contentDescription = null,
                            contentScale = ContentScale.FillHeight,
                        )
                        BasicTextField(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(5.dp))
                                .background(Color.LightGray)
                                .padding(16.dp)
                                .contentReceiver(
                                    receiveContentListener = { content ->
                                        println("here")
//                                        if (content.hasMediaType(MediaType.Image)) {
//                                            val clipData = content.clipEntry.clipData
//                                            for (index in 0 until clipData.itemCount) {
//                                                val item = clipData.getItemAt(index) ?: continue
//                                                imageUri = item.uri ?: continue
//                                            }
//                                        }
                                        content
                                    }
                                ),
                            value = text,
                            onValueChange = { text = it },
                            textStyle = TextStyle(fontSize = 14.sp),
                        )
                    }
                }
//                var offset by remember {
//                    mutableStateOf(Offset.Zero)
//                }
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .pointerInput(true) {
//                            detectDragGestures { change, _ ->
//                                offset = change.position
//                            }
//                        }
//                        .magnifier(
//                            sourceCenter = {
//                                offset
//                            },
//                            magnifierCenter = {
//                                offset// - Offset(x = 0f, y = 200f)
//                            },
//                            size = DpSize(150.dp, 150.dp),
//                            cornerRadius = 150.dp,
//                            zoom = 2.5f,
//                        ),
////                        .drawWithContent {
////                            drawContent()
////                            drawCircle(
////                                color = Color.Red,
////                                center = offset,
////                            )
////                        },
//                    contentAlignment = Alignment.Center,
//                ) {
//                    Image(
//                        modifier = Modifier.fillMaxWidth(),
//                        contentScale = ContentScale.FillWidth,
//                        painter = painterResource(id = R.drawable.kermit),
//                        contentDescription = null
//                    )
////                    Text(
////                        modifier = Modifier
////                            .basicMarquee(
////                                iterations = Int.MAX_VALUE
////                            ),
////                        text = "long text sample. long text sample. long text sample. long text sample. long text sample. long text sample. long text sample. long text sample. long text sample. long text sample. long text sample. long text sample."
////                    )
//                }
            }
        }
    }
}
