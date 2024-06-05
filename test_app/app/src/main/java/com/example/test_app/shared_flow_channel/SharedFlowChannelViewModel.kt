package com.example.test_app.shared_flow_channel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SharedFlowChannelViewModel : ViewModel() {
    private val _sharedFlow = MutableSharedFlow<Int>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    private val channel = Channel<Int>()
    val flow = channel.receiveAsFlow()

    init {
        viewModelScope.launch {
            repeat(100000) {
                _sharedFlow.emit(it)
                delay(1000)
            }
        }
        viewModelScope.launch {
            repeat(100000) {
                channel.send(it)
                delay(1000)
            }
        }
    }
}