package com.example.searchfieldcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.searchfieldcompose.ui.theme.SearchFieldComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SearchFieldComposeTheme {
                val viewModel = viewModel<MainViewModel>()
                val searchText by viewModel.searchText.collectAsState()
                val persons by viewModel.persons.collectAsState()
                val isSearching by viewModel.isSearching.collectAsState()
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    TextField(
                        value = searchText,
                        onValueChange = viewModel::onSearchTextChange,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(text = "Search") }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    if(isSearching) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            items(persons) { person ->
                                Text(
                                    text = "${person.firstName} ${person.lastName}",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
