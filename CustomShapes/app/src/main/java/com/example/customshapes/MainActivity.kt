package com.example.customshapes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.customshapes.ui.theme.CustomShapesTheme
import com.example.customshapes.ui.theme.SpeechBubbleShape

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CustomShapesTheme {
            }
        }
    }
}

@Preview
@Composable
private fun SpeechBubblePreview() {
    CustomShapesTheme {
        val tipSize = with(LocalDensity.current) { 15.dp.toPx() }
        val cornerRadius = with(LocalDensity.current) { 15.dp.toPx() }
        Box(
            modifier = Modifier
                .size(width = 200.dp, height = 100.dp)
                .clip(SpeechBubbleShape())
                .background(Color.Red)
        ) {
            Text(
                modifier = Modifier
                    .offset(x = 30.dp, y = 15.dp),
                text = "hello",
            )
        }
    }
}

