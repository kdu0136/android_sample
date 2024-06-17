package com.example.material3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Money
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign

@Composable
fun TextScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        var filledText by remember {
            mutableStateOf("")
        }
        TextField(
            value = filledText,
            onValueChange = { filledText = it },
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Right,
            ),
//            label = { Text(text = "label") },
            placeholder = { Text(text = "placeholder") },
            leadingIcon = {
                Icon(imageVector = Icons.Outlined.Money, contentDescription = "")
            },
//            trailingIcon = {
//                Text(text = "trailingIcon")
//            },
//            prefix = {
//                Text(text = "prefix")
//            },
            suffix = {
                Text(text = "won")
            },
            supportingText = {
                Text(text = "supporting text")
            },
//            isError = false,
//            visualTransformation = PasswordVisualTransformation()
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next,
            ),
//            keyboardActions = KeyboardActions(
//                onNext = {
//                    println("click next")
//                }
//            )
        )
        OutlinedTextField(
            value = filledText,
            onValueChange = { filledText = it },
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Right,
            ),
//            label = { Text(text = "label") },
            placeholder = { Text(text = "placeholder") },
            leadingIcon = {
                Icon(imageVector = Icons.Outlined.Money, contentDescription = "")
            },
//            trailingIcon = {
//                Text(text = "trailingIcon")
//            },
//            prefix = {
//                Text(text = "prefix")
//            },
            suffix = {
                Text(text = "won")
            },
            supportingText = {
                Text(text = "supporting text")
            },
//            isError = false,
//            visualTransformation = PasswordVisualTransformation()
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next,
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    println("click next")
                }
            )
        )
    }
}