package com.example.localsearchwithoutroom

import androidx.compose.runtime.Stable

@Stable
data class TodoListState(
    val searchQuery: String = "",
    val todos: List<Todo> = emptyList(),
)