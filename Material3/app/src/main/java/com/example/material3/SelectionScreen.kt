package com.example.material3

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp

@Composable
fun SelectionScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkboxes()

            MySwitch()

            RadioButtons()
        }
    }
}

data class ToggleableInfo(
    val isChecked: Boolean,
    val text: String,
)

@Composable
fun RadioButtons() {
    val radioButtons = remember {
        mutableStateListOf(
            ToggleableInfo(true, "title1"),
            ToggleableInfo(false, "title2"),
            ToggleableInfo(false, "title3")
        )
    }

    radioButtons.forEach { info ->
        Row(
            modifier = Modifier
                .padding(start = 16.dp)
                .clickable {
                    radioButtons.replaceAll {
                        it.copy(
                            isChecked = it.text == info.text
                        )
                    }
                }
                .padding(end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RadioButton(
                selected = info.isChecked,
                onClick = {
                    radioButtons.replaceAll {
                        it.copy(
                            isChecked = it.text == info.text
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = info.text)
        }
    }
}

@Composable
fun MySwitch() {
    var switch by remember {
        mutableStateOf(
            ToggleableInfo(
                false,
                "switch"
            )
        )
    }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = switch.text)
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = switch.isChecked,
            onCheckedChange = { isChecked ->
                switch = switch.copy(isChecked = isChecked)
            },
            thumbContent = {
                Icon(
                    imageVector = if(switch.isChecked) Icons.Default.Check else Icons.Default.Close,
                    contentDescription = null,
                )
            }
        )
    }
}

@Composable
fun Checkboxes() {
    val checkboxes = remember {
        mutableStateListOf(
            ToggleableInfo(false, "title1"),
            ToggleableInfo(false, "title2"),
            ToggleableInfo(false, "title3")
        )
    }

    var triState by remember {
        mutableStateOf(ToggleableState.Indeterminate)
    }
    val toggleTriState = {
        triState = when (triState) {
            ToggleableState.Indeterminate -> ToggleableState.On
            ToggleableState.On -> ToggleableState.Off
            else -> ToggleableState.On
        }
        checkboxes.indices.forEach { index ->
            checkboxes[index] = checkboxes[index].copy(isChecked = triState == ToggleableState.On)
        }
    }

    Row(
        modifier = Modifier
            .clickable { toggleTriState() }
            .padding(end = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TriStateCheckbox(
            state = triState,
            onClick = toggleTriState
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "tri state")
    }

    checkboxes.forEachIndexed { index, info ->
        Row(
            modifier = Modifier
                .padding(start = 16.dp)
                .clickable {
                    checkboxes[index] = checkboxes[index].copy(isChecked = !info.isChecked)
                }
                .padding(end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = info.isChecked,
                onCheckedChange = {
                    checkboxes[index] = checkboxes[index].copy(isChecked = it)
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = info.text)
        }
    }
}