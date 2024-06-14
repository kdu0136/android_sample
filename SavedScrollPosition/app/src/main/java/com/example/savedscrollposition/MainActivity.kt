package com.example.savedscrollposition

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.savedscrollposition.ui.theme.SavedScrollPositionTheme
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce

@OptIn(FlowPreview::class)
class MainActivity : ComponentActivity() {
    private val pref by lazy {
        applicationContext.getSharedPreferences("MyPrefs", MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SavedScrollPositionTheme {
                val lazyListState = rememberLazyListState(
                    initialFirstVisibleItemIndex = pref.getInt("firstVisibleItemIndex", 0),
                )

                LaunchedEffect(lazyListState) {
                    snapshotFlow {
                        lazyListState.firstVisibleItemIndex
                    }.debounce(500L)
                        .collect {
                            pref.edit().putInt("firstVisibleItemIndex", it).apply()
                        }
                }

                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(100) {
                        Text(
                            text = "Item $it",
                            modifier = Modifier
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}
