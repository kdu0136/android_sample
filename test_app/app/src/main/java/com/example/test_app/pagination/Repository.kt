package com.example.test_app.pagination

import kotlinx.coroutines.delay

class Repository {
    private val dataSource = (1..100).map {
        ListItem(
            title = "Title $it",
            description = "Description $it"
        )
    }

    suspend fun getItems(page: Int, pageSize: Int): Result<List<ListItem>> {
        delay(2000)
        val startingIndex = (page - 1) * pageSize
        return if (startingIndex + pageSize > dataSource.size) {
            Result.success(emptyList())
        } else {
            Result.success(dataSource.slice(startingIndex until startingIndex + pageSize))
        }
    }
}
