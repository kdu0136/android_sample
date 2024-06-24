package com.example.localsearchwithoutroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.localsearchwithoutroom.ui.theme.LocalSearchWithoutRoomTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MainViewModel(TodoSearchManager(applicationContext)) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LocalSearchWithoutRoomTheme {
                val state by viewModel.state.collectAsState()
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    TextField(
                        value = state.searchQuery,
                        onValueChange = viewModel::onSearchQueryChange,
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        items(
                            state.todos,
                            key = { it.id }
                        ) { todo ->
                            TodoItem(
                                todo = todo,
                                onDoneChange = viewModel::onDoneChange
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun TodoItem(
        modifier: Modifier = Modifier,
        todo: Todo,
        onDoneChange: (Todo, Boolean) -> Unit,
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clickable { onDoneChange(todo, !todo.isDone) }
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = todo.title,
                    fontSize = 16.sp
                )
                Text(
                    text = todo.text,
                    fontSize = 10.sp
                )
            }
            Checkbox(
                checked = todo.isDone,
                onCheckedChange = { onDoneChange(todo, it)  }
            )
        }
    }
}
