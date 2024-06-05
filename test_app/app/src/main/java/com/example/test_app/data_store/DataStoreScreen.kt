package com.example.test_app.data_store

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import kotlinx.coroutines.launch


val Context.dataStore: DataStore<AppSettings> by dataStore(
    "app-settings.json",
    AppSettingsSerializer
)

@Composable
fun DataStoreScreen() {
    val context = LocalContext.current
    val appSettings = context.dataStore.data.collectAsState(
        initial = AppSettings()
    ).value
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        for (language in Language.entries) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = language == appSettings.language,
                    onClick = {
                        scope.launch {
                            context.dataStore.updateData {
                                it.copy(language = language)
                            }
                        }
                    },
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = language.name, fontSize = 15.sp)
            }
        }
    }
}