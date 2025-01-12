package hr.foi.air.heatmapreport.view.data.helpers

import android.util.Log
import java.util.Date

class DateTimeConverter {
    fun ConvertDateToFormat(date: Date, format:String): String {
        val dateFormat = java.text.SimpleDateFormat(format)
        return dateFormat.format(date)

    }
    fun ConvertDateToFormat(dateString: String, format: String): String {
        return try {
            val inputDateFormat = java.text.SimpleDateFormat("dd/MM/yyyy")
            val date = inputDateFormat.parse(dateString)

            val outputDateFormat = java.text.SimpleDateFormat(format)
            Log.d("ConvertDateToFormat", "Formatted date: ${outputDateFormat.format(date)}")
            date?.let { outputDateFormat.format(it) } ?: "Invalid Date"
        } catch (e: Exception) {
            Log.e("ConvertDateToFormat", "Error", e)
            "Invalid Format"
        }
    }

}