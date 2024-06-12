package com.example.observeconnectivity

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserve {
    fun observe(): Flow<Status>

    enum class Status {
        Available, Unavailable, Losing, Lost
    }
}