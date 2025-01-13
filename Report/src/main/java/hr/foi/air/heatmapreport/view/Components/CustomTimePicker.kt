package hr.foi.air.heatmapreport.view.Components

import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Create
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier

import androidx.compose.material3.TextField
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialWithDialogExample(
    onConfirm: (TimePickerState) -> Unit,
    onDismiss: () -> Unit,
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    TimePickerDialog(
        onDismiss = { onDismiss() },
        onConfirm = { onConfirm(timePickerState) }
    ) {
        TimePicker(
            state = timePickerState,
        )
    }
}

@Composable
fun TimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Dismiss")
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text("OK")
            }
        },
        text = { content() }
    )
}



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