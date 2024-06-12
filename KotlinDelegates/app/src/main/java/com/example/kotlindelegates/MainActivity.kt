package com.example.kotlindelegates

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.lerp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.kotlindelegates.ui.theme.KotlinDelegatesTheme
import kotlin.reflect.KProperty

class MainActivity : ComponentActivity(), AnalyticsLogger by AnalyticsLoggerImpl() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerLifecycleOwner(this)
    }
}

class MyLazy<out T>(
    private val initialize: () -> T
) {
    private var value: T? = null
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value ?: let {
            value = initialize()
            value!!
        }
    }
}

interface AnalyticsLogger {
    fun registerLifecycleOwner(owner: LifecycleOwner)
}

class AnalyticsLoggerImpl: AnalyticsLogger, LifecycleEventObserver {
    override fun registerLifecycleOwner(owner: LifecycleOwner) {
        owner.lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_RESUME -> println("User opened the screen")
            Lifecycle.Event.ON_PAUSE -> println("User leaves the screen")
            else -> Unit
        }
    }
}
