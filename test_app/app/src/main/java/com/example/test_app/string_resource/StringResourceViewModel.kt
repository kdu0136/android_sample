package com.example.test_app.string_resource

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_app.R
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class StringResourceViewModel: ViewModel() {
    private val errorChannel: Channel<UiText> = Channel()
    val errors = errorChannel.receiveAsFlow()

    fun getNewMessage() {
        viewModelScope.launch {
            errorChannel.send(
                when(Random.nextBoolean()) {
                    true -> UiText.StringResource(R.string.string_resource)
                    false -> UiText.DynamicString("dynamic string")
                }
            )
        }
    }
}