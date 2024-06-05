package com.example.test_app.pagination

interface Paginator<Key, Item> {
    suspend fun loadNextItems()
    fun reset()
}