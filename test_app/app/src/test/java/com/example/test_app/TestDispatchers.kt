package com.example.test_app

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher

class TestDispatchers: DispatcherProvider {
    val testDispatcher = StandardTestDispatcher()
    override val main: CoroutineDispatcher
        get() = testDispatcher
    override val io: CoroutineDispatcher
        get() = testDispatcher
    override val default: CoroutineDispatcher
        get() = testDispatcher
}