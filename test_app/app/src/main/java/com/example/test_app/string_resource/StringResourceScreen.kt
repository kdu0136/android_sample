package com.example.test_app.string_resource

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun StringResourceScreen() {
    val viewModel = viewModel<StringResourceViewModel>()
    val message = viewModel.errors.collectAsState(initial = null)
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(onClick = viewModel::getNewMessage) {
            Text(text = "get new message")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = message.value?.asString() ?: "",
            fontSize = 20.sp
        )
    }
}