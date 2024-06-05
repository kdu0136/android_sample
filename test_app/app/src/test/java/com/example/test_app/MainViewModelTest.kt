package com.example.test_app

import io.kotest.core.spec.style.FunSpec
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest : FunSpec({
    val testDispatchers = TestDispatchers()
})
