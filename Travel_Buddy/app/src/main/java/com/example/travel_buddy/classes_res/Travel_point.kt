package com.example.travel_buddy.classes_res

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Date(dateString: String? = null) {
    var day: Int
    var month: Int
    var year: Int
    var hour: Int
    var minute: Int
    var calendar: Calendar

    init {
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())


        calendar = Calendar.getInstance()

        if (dateString != null) {
            try {
                val date = format.parse(dateString)
                calendar.time = date // Set parsed date to calendar
            } catch (e: Exception) {
                throw IllegalArgumentException("Invalid date format. Should be DD/MM/YYYY HH:MM")
            }
        }

        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH) + 1
        year = calendar.get(Calendar.YEAR)
        hour = calendar.get(Calendar.HOUR_OF_DAY)
        minute = calendar.get(Calendar.MINUTE)
    }


    override fun toString(): String {
        return "$day/$month/$year $hour:$minute"
    }
}

class Travel_point(
    val name: String = "No name point",
    val date: Date  = Date()
){
    fun test(){ Log.d("mes","works $date")}
}