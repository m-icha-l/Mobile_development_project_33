package com.example.travel_buddy.classes_res

import Date
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
    var location: String = "",
    var newId: Int = 0,
    var travel_plan_name: String = "No travel plan name"
)

fun dbTravel_point.translateFromDb(): Travel_point {
    return Travel_point(
        name = this.name,
        date = Date(this.date), // Tworzy obiekt klasy Date
        location = parseLocation(this.location), // Tworzy obiekt android.location.Location
        newId = this.newId, // Przekazujemy ID
        travel_plan_name = this.travel_plan_name // Przekazujemy travel_plan_name
    )
}

open class Travel_point(
    val name: String = "No name point",
    val date: Date = Date(), // in Helper_class
    var location: Location? = null,
    var newId: Int = 0,  // Przekazujemy ID
    var travel_plan_name: String = "No travel plan name" // Przekazujemy travel_plan_name
) {
    private var dbRef: dbTravel_point

    init {
        if (location == null) {
            location = Location("default").apply { // placeholder waiting for Mikłaj's function
                latitude = 0.0
                longitude = 0.0
            }
        }
        // Inicjalizujemy dbRef z przekazanymi wartościami
        dbRef = dbTravel_point(
            name = name,
            date = date.toString(),
            location = location?.let { formatLocation(it) } ?: "",
            newId = newId,
            travel_plan_name = travel_plan_name
        )
    }

    fun getDb(): dbTravel_point {
        return dbRef
    }

    open fun ToDb(): Any {
        return dbTravel_point(
            name = this.name,
            date = this.date.toString(), // Klasa Date ma nadpisane toString()
            location = this.location?.let { formatLocation(it) } ?: "",
            newId = this.newId,
            travel_plan_name = this.travel_plan_name
        )
    }

    override fun toString(): String {
        return "Name: $name, Date: $date, $location"
    }
}
