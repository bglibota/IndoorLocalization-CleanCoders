package com.example.indoorlocalizationcleancoders.navigation


import android.annotation.SuppressLint
import android.icu.util.Calendar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField


import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.indoorlocalizationcleancoders.R
import com.example.indoorlocalizationcleancoders.components.CustomDatePicker
import com.example.indoorlocalizationcleancoders.components.CustomTimePicker
import com.example.indoorlocalizationcleancoders.components.HeaderComponent
import java.util.Date


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeatmapReportView(navController: NavController){
    var text:String = "Hello"
    var selectedTime = remember { mutableStateOf("12:00") }
    val context = LocalContext.current
    val datePicker = remember { CustomDatePicker(context) }
    val selectedDate = remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            HeaderComponent(
                title = "Generate new report",
                onBackPressed = {
                   navController.navigate("home")
                }
            )
        }
    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )  {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            shape = MaterialTheme.shapes.small,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Text showing selected date
                Column(
                    modifier = Modifier.weight(1f) // Makes the text take the available space
                ) {
                    Text(
                        text = "Selected date:",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f) // Subtitle style
                    )
                    Text(
                        text = selectedDate.value,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                // Calendar button
                Button(
                    onClick = {
                        datePicker.show { date ->
                            selectedDate.value = date
                        }
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.heroicons_calendar), // Use your drawable
                        contentDescription = "calendar",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
        CustomTimePicker(
            title = "Time From",

            initialHour = (Date()).hours,
            initialMinute = (Date()).minutes,
            onTimeSelected = { time -> selectedTime.value = time }
        )
        CustomTimePicker(
            title = "Time Until",

            initialHour = (Date()).hours,
            initialMinute = (Date()).minutes,
            onTimeSelected = { time -> selectedTime.value = time }
        )
        Button(onClick = {
          //  navController.navigate("GenerateReport")
        }) {
            Text(text = "Generate")
        }

    }
    }
}






