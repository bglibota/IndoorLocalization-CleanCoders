@file:OptIn(ExperimentalMaterial3Api::class)

package hr.foi.air.heatmapreport.view.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.text.SimpleDateFormat
import java.util.*

fun getFormattedDate(timeInMillis: Long): String{
    val calender = Calendar.getInstance()
    calender.timeInMillis = timeInMillis
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    return dateFormat.format(calender.timeInMillis)
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerSample(state: DateRangePickerState){
    val dateFormat="dd.MM.yyyy."
    DateRangePicker(state,
        modifier = Modifier,
        dateFormatter = CustomDatePickerFormatter(dateFormat, dateFormat, dateFormat),
        title = null,
        headline = {
            DateRangePickerDefaults.DateRangePickerHeadline(
                selectedStartDateMillis = state.selectedStartDateMillis,
                selectedEndDateMillis = state.selectedEndDateMillis,
                displayMode = state.displayMode,
                dateFormatter = CustomDatePickerFormatter(dateFormat, dateFormat, dateFormat),
                modifier = Modifier.padding(10.dp)


            )

        },
        showModeToggle = false,
        colors = DatePickerDefaults.colors(
            containerColor = Color.Blue,
            titleContentColor = Color.Black,
            headlineContentColor = Color.Black,
            weekdayContentColor = Color.Black,
            subheadContentColor = Color.Black,
            yearContentColor = Color.Green,
            currentYearContentColor = Color.Red,
            selectedYearContainerColor = Color.Red,
            disabledDayContentColor = Color.Gray,
            todayDateBorderColor = Color.Blue,
            dayInSelectionRangeContainerColor = Color.LightGray,
            dayInSelectionRangeContentColor = Color.White,
            selectedDayContainerColor = Color.Black
        )
    )
}
@Composable
fun DateRangePickerDialog(
    isVisible: MutableState<Boolean>,
    onDateRangeSelected: (startDate: String?, endDate: String?) -> Unit
) {
    if (isVisible.value) {
        Dialog(onDismissRequest = { isVisible.value = false }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp)
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
            ) {
                val state = rememberDateRangePickerState()
                Column {
                    Column {



                        TopAppBar(
                            title = {
                                Text(text = "Select Date Range")
                            },
                            actions = {
                                IconButton(onClick = {
                                    isVisible.value=false
                                    onDateRangeSelected(
                                        state.selectedStartDateMillis?.let { getFormattedDate(it) },
                                        state.selectedEndDateMillis?.let { getFormattedDate(it) }
                                    )
                                }) {
                                    Icon(Icons.Default.Check, contentDescription = "Save")
                                }
                            }
                        )
                    }
                    DateRangePickerSample(state)

                }
            }
        }
    }
}
class CustomDatePickerFormatter(
    private val startFormat: String,
    private val rangeFormat: String,
    private val endFormat: String
) : DatePickerFormatter {

    override fun formatDate(
        dateMillis: Long?,
        locale: CalendarLocale,
        forContentDescription: Boolean
    ): String? {
        if (dateMillis == null) return null

        val javaLocale = Locale(locale.language, locale.country)

        val dateFormat = SimpleDateFormat(
            if (forContentDescription) rangeFormat else startFormat,
            javaLocale
        )
        return dateFormat.format(Date(dateMillis))
    }

    override fun formatMonthYear(monthMillis: Long?, locale: CalendarLocale): String? {
        if (monthMillis == null) return null

        val javaLocale = Locale(locale.language, locale.country)

        val monthYearFormat = SimpleDateFormat("MMMM yyyy", javaLocale)
        return monthYearFormat.format(Date(monthMillis))
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview16() {
    val selectedStartTime = remember { mutableStateOf("12:00") }
    val selectedEndTime = remember { mutableStateOf("12:00") }
    val selectedStartDate = remember { mutableStateOf<String?>(null) }
    val selectedEndDate = remember { mutableStateOf<String?>(null) }
    val showDatePicker = remember { mutableStateOf(true) }


    DateRangePickerDialog(
        isVisible = showDatePicker,
        onDateRangeSelected = { startDate, endDate ->
            selectedStartDate.value = startDate
            selectedEndDate.value = endDate
            if(endDate!=null && startDate!=null){
                showDatePicker.value=false
            }
        }
    )
}