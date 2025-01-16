package hr.foi.air.heatmapreport.view.data.helpers

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

class DateTimeConverter {
    fun ConvertDateToFormat(date: Date, format:String): String {
        val dateFormat = java.text.SimpleDateFormat(format)
        return dateFormat.format(date)

    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun ConvertDateToFormat(dateString: String, outputFormat: String): String {
        return try {
            val inputDateFormat = java.text.SimpleDateFormat("dd/MM/yyyy")
            val date = inputDateFormat.parse(dateString)

            val outputDateFormat = java.text.SimpleDateFormat(outputFormat)
            Log.d("ConvertDateToFormat", "Formatted date: ${outputDateFormat.format(date)}")
            date?.let { outputDateFormat.format(it) } ?: "Invalid Date"
        } catch (e: Exception) {
            Log.e("ConvertDateToFormat", "Error", e)
            "Invalid Format"
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertDateTimeToCustomFormat(dateTimeString: String, format:String): String? {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")

        val localDateTime = try {
            LocalDateTime.parse(dateTimeString, inputFormatter)
        } catch (e: Exception) {
            return null
        }

        val outputFormatter = DateTimeFormatter.ofPattern(format)

        return localDateTime.format(outputFormatter)
    }


}