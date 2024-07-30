package com.dongun.canvas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dongun.canvas.ui.theme.CanvasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CanvasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier.padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        DrawableCanvas(modifier = Modifier.fillMaxSize())
                    }
                }
            }
        }
    }
}

@Composable
fun DrawableCanvas(modifier: Modifier = Modifier) {
    var pointsHistory by remember {
        mutableStateOf<List<List<Offset>>>(emptyList())
    }
    var points by remember {
        mutableStateOf<List<Offset>>(emptyList())
    }

    Canvas(
        modifier = modifier
            .pointerInput(key1 = Unit) {
                detectDragGestures(
                    onDragStart = { touch ->
                        println("start touch $touch")
                        points = listOf(touch)
                    },
                    onDrag = { change, dragAmount ->
                        println("drag touch $dragAmount, result: $change")
                        points = points + change.position
                    },
                    onDragEnd = {
                        pointsHistory = pointsHistory + listOf(points)
                        points = emptyList()
                        println()
                    }
                )
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

            drawPath(path, Color.Black, style = Stroke(width = 5.dp.toPx()))
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

                drawPath(path, Color.Black, style = Stroke(width = 5.dp.toPx()))
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
