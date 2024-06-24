package com.example.localsearchwithoutroom

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.random.Random

class MainViewModel(
    private val todoSearchManager: TodoSearchManager
) : ViewModel() {
    private val _state = MutableStateFlow(TodoListState())
    val state = _state.asStateFlow()
//    var state by mutableStateOf(TodoListState())
//        private set
    private var searchJob: Job? = null

    init {
        viewModelScope.launch {
            todoSearchManager.init()
//            val todos = (1..100).map {
//                Todo(
//                    namespace = "my_todos",
//                    id = UUID.randomUUID().toString(),
//                    score = 1,
//                    title = "Todo $it",
//                    text = "Description $it",
//                    isDone = Random.nextBoolean()
//                )
//            }
//            todoSearchManager.putTodos(todos)
        }
    }

    fun onSearchQueryChange(query: String) {
        _state.update {
            state.value.copy(searchQuery = query)
        }

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            val todos = todoSearchManager.searchTodos(query)
            println(todos.size)
            _state.update {
                state.value.copy(todos = todos)
            }
        }
    }

    fun onDoneChange(todo: Todo, isDone: Boolean) {
        viewModelScope.launch {
            todoSearchManager.putTodos(listOf(todo.copy(isDone = isDone)))
            _state.update {
                state.value.copy(
                    todos = state.value.todos.map {
                        if (it.id == todo.id) todo.copy(isDone = isDone) else it
                    }
                )
            }
        }
    }

    override fun onCleared() {
        todoSearchManager.closeSession()
        super.onCleared()
    }
}