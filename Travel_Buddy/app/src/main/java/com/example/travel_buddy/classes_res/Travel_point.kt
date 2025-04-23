package com.example.travel_buddy.classes_res

import android.util.Log

import android.location.Location
import androidx.room.Entity

import java.text.SimpleDateFormat
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import androidx.room.PrimaryKey
import com.example.travel_buddy.functions.formatLocation
import com.example.travel_buddy.functions.parseLocation
import com.example.travel_buddy.classes_res.Duration
import com.example.travel_buddy.classes_res.heritage_points.translateFromDb

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale

@Entity(tableName = "travel_points")
data class dbTravel_point(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // DO WYJEBANIA, jeśli ID jest generowane przez bazę danych
    val name: String = "No name point",
    val date: String = "day/month/year hour:minute",
    val end_date: String = "day/month/year hour:minute",
    val time: String = "days, hours:minutes",
    var location: String = "",
    var newId: Int = 0,
    var travel_plan_name: String = "No travel plan name",
    var notes: String  = ""
)

fun dbTravel_point.translateFromDb(): Travel_point {
    return Travel_point(
        name = this.name,
        date = Date(this.date),
        end_date = Date(this.end_date),
        time = Date(this.date) - Date(this.end_date),
        location = parseLocation(this.location),
        newId = this.newId,
        travel_plan_name = this.travel_plan_name,
        notes = this.notes
    )
}

open class Travel_point(
    val name: String = "No name point",
    val date: Date = Date(),
    var end_date: Date = date,
    var time: Duration? = null,
    var location: Location? = null,
    var notes: String = "",
    var newId: Int = 0,
    var travel_plan_name: String = "No travel plan name"

) {
    init {
        if(time == null)
        {
            time = end_date - date
        }
    }

    fun getDb(): dbTravel_point {
        return dbTravel_point(
            name = name,
            date = date.toString(),
            end_date = end_date.toString(),
            location = location?.let { formatLocation(it) } ?: "",
            newId = newId,
            travel_plan_name = travel_plan_name,
            notes = notes
        )
    }

    open fun ToDb(): Any {
        return dbTravel_point(
            name = this.name,
            date = this.date.toString(),
            end_date = this.end_date.toString(),
            time = this.time.toString(),
            location = this.location?.let { formatLocation(it) } ?: "",
            newId = this.newId,
            travel_plan_name = this.travel_plan_name,
            notes = notes
        )
    }

    override fun toString(): String {
        return "Name: $name, Date: $date,Date: $end_date, $location, ID: $newId"
    }
}
