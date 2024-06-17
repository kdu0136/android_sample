package com.example.material3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ButtonScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Confirm")
        }

        ElevatedButton(onClick = { /*TODO*/ }) {
            Icon(
                modifier = Modifier.size(18.dp),
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Add")
        }

        FilledTonalButton(onClick = { /*TODO*/ }) {
            Text(text = "Open in browse")
        }

        OutlinedButton(onClick = { /*TODO*/ }) {
            Text(text = "Back")
        }

        TextButton(onClick = { /*TODO*/ }) {
            Text(text = "Learn more")
        }
    }
}