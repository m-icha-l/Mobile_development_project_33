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
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale


@Entity(tableName = "travel_points")
data class dbTravel_point(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "No name point",
    var travel_plan_name: String = "No travel plan name",
    val date: String = "day/month/year hour:minute",
    var location: String = ""
)

open class Travel_point(
    val name: String = "No name point",
    val date: Date  = Date(), //in Helper_class
    var location: Location? = null,
    var travel_plan_name: String = "No travel plan name",
){
    private var dbRef: dbTravel_point
    init {
        if (location == null) {

            //location = get_current_location

            location = Location("default").apply { // placeholder waiting for Mikłaj's function
                latitude = 0.0
                longitude = 0.0
            }
        }
        dbRef = dbTravel_point(travel_plan_name = travel_plan_name, name = name, date = date.toString(), location = location.toString())
    }

    fun getDb(): dbTravel_point {
        return dbRef
    }

    override fun toString(): String {
        return "Name: $name, Date: $date, $location"
    }
}