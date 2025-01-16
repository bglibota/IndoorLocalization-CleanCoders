package hr.foi.air.heatmapreport.view.Components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties

@Composable
fun ShowDialog(showDialog: MutableState<Boolean>, title:String, text:String){
    AlertDialog(
    onDismissRequest = {
        showDialog.value = false
    },
    modifier = Modifier,
    properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
    title = {
        Text(text = title)
    },
    text = {
        Text(text = text)
    },
    confirmButton = {
        Button(onClick = {
            showDialog.value = false
        }) {
            Text("OK")
        }
    }
    )
}