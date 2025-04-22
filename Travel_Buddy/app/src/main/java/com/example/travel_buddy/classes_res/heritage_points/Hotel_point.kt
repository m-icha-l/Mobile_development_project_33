package com.example.travel_buddy.classes_res.heritage_points

import com.example.travel_buddy.classes_res.Date
import com.example.travel_buddy.classes_res.Duration
import android.location.Location
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.travel_buddy.classes_res.Travel_point
import com.example.travel_buddy.classes_res.dbTravel_point
import com.example.travel_buddy.functions.formatLocation
import com.example.travel_buddy.functions.parseLocation

@Entity(tableName = "hotel_points")
data class dbHotel_point(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "No name point",
    val city: String = "",
    val date: String = "day/month/year hour:minute",
    val end_date: String = "day/month/year hour:minute",
    val time: String = "hour:minute",
    var location: String = "",
    var newId: Int = 0,
    var travel_plan_name: String = "No travel plan name",
    var notes: String = ""
)

fun dbHotel_point.translateFromDb(): Hotel_point {
    return Hotel_point(
        name = this.name,
        city = this.city,
        date = Date(this.date),
        end_date = Date(this.end_date),
        time = Date(this.date) - Date(this.end_date),
        location = parseLocation(this.location),
        newId = this.newId,
        travel_plan_name = this.travel_plan_name,
        notes = this.notes
    )
}

class Hotel_point(
    name: String = "No name point",
    val city: String = "",
    date: Date = Date(),
    end_date: Date = date,
    time: Duration = end_date - date,
    location: Location? = null,
    newId: Int = 0,
    travel_plan_name: String = "No travel plan name",
    notes: String=""
) : Travel_point(name, date,end_date,time, location,notes, newId, travel_plan_name) {

    fun getDbObject(): dbHotel_point {
        return dbHotel_point(
            name = name,
            city = city,
            date = date.toString(),
            end_date = end_date.toString(),
            location = location?.let { formatLocation(it) } ?: "",
            newId = this.newId,
            travel_plan_name = this.travel_plan_name,
            notes = this.notes
        )
    }

    override fun toString(): String {
        return "Name: $name, Date: $date, End reservation date: $end_date, $location, City: $city, ID: $newId, Travel Plan: $travel_plan_name"
    }

    override fun ToDb(): dbHotel_point {
        return dbHotel_point(
            name = this.name,
            city = this.city,
            date = this.date.toString(),
            end_date = this.end_date.toString(),
            time = this.time.toString(),
            location = (this.location?.let { formatLocation(it) } ?: ""),
            newId = this.newId,
            travel_plan_name = this.travel_plan_name,
            notes = this.notes
        )
    }
}
