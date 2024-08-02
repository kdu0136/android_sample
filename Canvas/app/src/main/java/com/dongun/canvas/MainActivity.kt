package com.dongun.canvas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dongun.canvas.ui.theme.CanvasTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalLayoutApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CanvasTheme {
                var t by remember {
                    mutableStateOf("가나다라마바사아자차카타파하abcdefghijklmnopqrstuvwxyz")
                }
                var t2 by remember {
                    mutableStateOf("")
                }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                    ) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(text = "Text 123")
                            DrawableCanvas(
                                modifier = Modifier
                                    .size(100.dp)
                                    .background(Color.Red)
                            )
                            Text(
                                text = t,
                                maxLines = 1,
                                onTextLayout = { result ->
                                    if (result.hasVisualOverflow) {
                                        val lineEndIndex = result.getLineEnd(
                                            lineIndex = 0,
                                            visibleEnd = true
                                        )
                                        val temp = t.substring(0, lineEndIndex)
                                        val temp2 = t.substring(lineEndIndex, t.length)
                                        t = temp
                                        t2 = temp2
                                    }
                                }
                            )
                        }
                        Row(modifier = Modifier.fillMaxWidth()) {
                            println("t2: $t2")
                            Text(
                                text = t2,
                                maxLines = 1,
                                onTextLayout = { result ->
                                    if (result.hasVisualOverflow) {
                                        val lineEndIndex = result.getLineEnd(
                                            lineIndex = 0,
                                            visibleEnd = true
                                        )
                                        val temp = t2.substring(0, lineEndIndex)
                                        val temp2 = t2.substring(lineEndIndex, t2.length)
                                        println("temp: $temp")
                                        println("temp2: $temp2")
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


fun isOffsetWithinIntSize(intSize: IntSize, offset: Offset): Boolean {
    return offset.x >= 0 && offset.x <= intSize.width &&
            offset.y >= 0 && offset.y <= intSize.height
}

@Composable
fun DrawableCanvas(modifier: Modifier = Modifier) {
    var pointsHistory by remember {
        mutableStateOf<List<List<Offset>>>(emptyList())
    }
    var points by remember {
        mutableStateOf<List<Offset>>(emptyList())
    }
    var size = remember {
        IntSize(0, 0)
    }

    Canvas(
        modifier = modifier
            .pointerInput(key1 = Unit) {
                detectDragGestures(
                    onDragStart = { touch ->
                        points = listOf(touch)
                    },
                    onDrag = { change, dragAmount ->
                        if (isOffsetWithinIntSize(size, change.position)) {
                            points = points + change.position
                        } else {
                            pointsHistory = pointsHistory + listOf(points)
                            points = emptyList()
                        }
                    },
                    onDragEnd = {
                        pointsHistory = pointsHistory + listOf(points)
                        points = emptyList()
                    }
                )
            }
            .onGloballyPositioned {
                size = it.size
            }
    ) {
        if (points.size > 1) {
            val path = Path().apply {
                val firstPoint = points.first()
                val rest = points.subList(1, points.size - 1)

                moveTo(firstPoint.x, firstPoint.y)
                rest.forEach {
                    lineTo(it.x, it.y)
                }
            }

            drawPath(path, Color.Black, style = Stroke(width = 3.dp.toPx()))
        }

        pointsHistory.forEach { points ->
            if (points.size > 1) {
                val path = Path().apply {
                    val firstPoint = points.first()
                    val rest = points.subList(1, points.size - 1)

                    moveTo(firstPoint.x, firstPoint.y)
                    rest.forEach {
                        lineTo(it.x, it.y)
                    }
                }

                drawPath(path, Color.Black, style = Stroke(width = 3.dp.toPx()))
            }
        }
    }
}

//@OptIn(ExperimentalTextApi::class)
@Composable
fun ExampleTextAnnotatedString() {
    val textMeasure = rememberTextMeasurer()

    val text = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color.White,
                fontSize = 22.sp,
                fontStyle = FontStyle.Italic
            )
        ) {
            append("Hello,")
        }
    }
    val text2 = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color.White,
                fontSize = 22.sp,
                fontStyle = FontStyle.Italic
            )
        ) {
            append("Hello2,")
        }
    }
    Canvas(
        modifier = Modifier
            .fillMaxSize(),
        onDraw = {
            drawRect(color = Color.Black)
            drawText(
                textMeasurer = textMeasure,
                text = text,
                topLeft = Offset(10.dp.toPx(), 10.dp.toPx())
            )
            drawText(
                textMeasurer = textMeasure,
                text = text2,
                topLeft = Offset(10.dp.toPx(), 10.dp.toPx())
            )
        },
    )
}
