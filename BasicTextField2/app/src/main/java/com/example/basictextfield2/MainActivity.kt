package com.example.basictextfield2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text2.BasicSecureTextField
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.InputTransformation
import androidx.compose.foundation.text2.input.TextFieldBuffer
import androidx.compose.foundation.text2.input.TextFieldCharSequence
import androidx.compose.foundation.text2.input.TextFieldLineLimits
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.basictextfield2.ui.theme.BasicTextField2Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BasicTextField2Theme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(
                        16.dp, Alignment.CenterVertically
                    ),
                ) {
                    var text1 by remember {
                        mutableStateOf("")
                    }
                    var text2 by remember {
                        mutableStateOf("")
                    }

                    val modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray)
                        .padding(16.dp)


                    LaunchedEffect(key1 = true) {
                        delay(5000)
                        text2 = "Hello World"
                    }

                    BasicTextField(
                        modifier = modifier,
                        value = text1,
                        onValueChange = { text1 = it }
                    )

                    val state = rememberTextFieldState()

                    LaunchedEffect(key1 = true) {
                        state.textAsFlow()
                            .collectLatest {
                                println("text change: $it")
                            }
                    }

                    BasicSecureTextField(
                        modifier = modifier,
                        state = state,
//                        inputTransformation = AndroidInputTransformation,
                        scrollState = rememberScrollState(),
//                        lineLimits = TextFieldLineLimits.MultiLine(1, 2)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
object AndroidInputTransformation: InputTransformation {
    override fun transformInput(
        originalValue: TextFieldCharSequence,
        valueWithChanges: TextFieldBuffer
    ) {
        if (!"Android".contains(valueWithChanges.asCharSequence())) {
            valueWithChanges.revertAllChanges()
        }
    }

}
