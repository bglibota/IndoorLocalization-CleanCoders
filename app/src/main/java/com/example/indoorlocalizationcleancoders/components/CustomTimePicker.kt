package com.example.indoorlocalizationcleancoders.components

import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Create
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import java.util.*
import androidx.compose.material3.TextField
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext

@Composable
fun CustomTimePicker(
    title:String,
    initialHour: Int = 0,
    initialMinute: Int = 0,
    onTimeSelected: (String) -> Unit
) {
    var timeText by remember { mutableStateOf("${String.format("%02d", initialHour)}:${String.format("%02d", initialMinute)}") }
    var isDialogOpen by remember { mutableStateOf(false) }


    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = timeText,
            onValueChange = { timeText = it },
            label = { Text(title) },
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { isDialogOpen = true }) {
                    Icon(imageVector = Icons.Sharp.Create, contentDescription = "Time Picker")
                }
            }
        )
    }


    if (isDialogOpen) {
        val currentHour = if (timeText.isNotEmpty()) timeText.split(":")[0].toInt() else initialHour
        val currentMinute = if (timeText.isNotEmpty()) timeText.split(":")[1].toInt() else initialMinute

        TimePickerDialog(
            LocalContext.current,
            { _, hourOfDay, minute ->
                timeText = "${String.format("%02d", hourOfDay)}:${String.format("%02d", minute)}"
                onTimeSelected(timeText)
            },
            currentHour,
            currentMinute,
            true
        ).apply {
            setCancelable(true)
            show()
        }
    }
}