package com.example.dropdownitem

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dropdownitem.ui.theme.DropDownItemTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DropDownItemTheme {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(
                        listOf(
                            "Philipp",
                            "Carl",
                            "Martin",
                            "Jake",
                            "Jake",
                            "Jake",
                            "Jake",
                            "Jake",
                            "Philipp",
                            "Philipp",
                        )
                    ) {
                        PersonItem(
                            personName = it,
                            dropdownItems = listOf(
                                DropDownItem("Item 1"),
                                DropDownItem("Item 2"),
                                DropDownItem("Item 3"),
                            ),
                            onItemClick = { item ->
                                Toast.makeText(
                                    applicationContext,
                                    item.text,
                                    Toast.LENGTH_LONG
                                ).show()
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}
