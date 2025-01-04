package hr.foi.air.heatmapreport.view.Components


import android.app.DatePickerDialog
import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

class CustomDatePicker(private val context: Context) {
    private val calendar = Calendar.getInstance()
    private var selectedDate: Date? = null

    private val dateFormatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    fun show(onDateSelected: (String) -> Unit) {
        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                selectedDate = calendar.time
                onDateSelected(dateFormatter.format(selectedDate!!))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

}