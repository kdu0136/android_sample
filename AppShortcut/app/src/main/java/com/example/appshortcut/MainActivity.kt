package com.example.appshortcut

import android.app.PendingIntent
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.getSystemService
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import com.example.appshortcut.ui.theme.AppShortcutTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleIntent(intent)

        setContent {
            AppShortcutTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        16.dp, Alignment.CenterVertically
                    )
                ) {
                    when(viewModel.shortcutType) {
                        ShortcutType.STATIC -> Text("Static shortcut clicked")
                        ShortcutType.DYNAMIC -> Text("Dynamic shortcut clicked")
                        ShortcutType.PINNED -> Text("Pinned shortcut clicked")
                        null -> Unit
                    }
                    Button(
                        onClick = ::addDynamicShortcut
                    ) {
                        Text("Add dynamic shortcut")
                    }
                    Button(
                        onClick = ::addPinnedShortcut
                    ) {
                        Text("Add pinned shortcut")
                    }
                    Button(
                        onClick = ::removePinnedShortcut
                    ) {
                        Text("Remove pinned shortcut")
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun addPinnedShortcut() {
        val shortcutManager = getSystemService(ShortcutManager::class.java)
        val isExistPinnedShortcut = shortcutManager.pinnedShortcuts.any {
            it.id == "pinned"
        }
        if (shortcutManager.isRequestPinShortcutSupported
            && !isExistPinnedShortcut) {
            val shortcut = ShortcutInfo.Builder(applicationContext, "pinned")
                .setShortLabel("pinned shortcut label")
                .setLongLabel("pinned shortcut long label...")
                .setIcon(
                    Icon.createWithResource(
                    applicationContext, R.drawable.baseline_baby_changing_station_24
                ))
                .setIntent(
                    Intent(applicationContext, MainActivity::class.java).apply {
                        action = Intent.ACTION_VIEW
                        putExtra("shortcutId", "pinned")
                    }
                )
                .build()

            val callbackIntent = shortcutManager.createShortcutResultIntent(shortcut)
            val successPendingIntent = PendingIntent.getBroadcast(
                applicationContext,
                0,
                callbackIntent,
                PendingIntent.FLAG_IMMUTABLE,
            )
            shortcutManager.requestPinShortcut(shortcut, successPendingIntent.intentSender)
        }
    }

    private fun removePinnedShortcut() {
        val shortcutManager = getSystemService(ShortcutManager::class.java)
        val isExistPinnedShortcut = shortcutManager.pinnedShortcuts.any {
            it.id == "pinned"
        }

        if (shortcutManager.isRequestPinShortcutSupported
            && isExistPinnedShortcut) {
            println("remove")
            shortcutManager.removeDynamicShortcuts(listOf("pinned"))
        }
    }

    private fun addDynamicShortcut() {
        val shortCut = ShortcutInfoCompat.Builder(applicationContext, "dynamic")
            .setShortLabel("dynamic shortcut label")
            .setLongLabel("dynamic shortcut long label...")
            .setIcon(IconCompat.createWithResource(
                applicationContext, R.drawable.baseline_baby_changing_station_24
            ))
            .setIntent(
                Intent(applicationContext, MainActivity::class.java).apply {
                    action = Intent.ACTION_VIEW
                    putExtra("shortcutId", "dynamic")
                }
            )
            .build()
        ShortcutManagerCompat.pushDynamicShortcut(applicationContext, shortCut)
    }

    private fun handleIntent(intent: Intent?) {
        intent?.let {
            when (intent.getStringExtra("shortcutId")) {
                "dynamic" -> viewModel.changeShortcutType(ShortcutType.DYNAMIC)
                "pinned" -> viewModel.changeShortcutType(ShortcutType.PINNED)
                "static" -> viewModel.changeShortcutType(ShortcutType.STATIC)
            }
        }
    }
}
