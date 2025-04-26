package com.example.travel_buddy.classes_res.heritage_points

import android.location.Location
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.travel_buddy.classes_res.Date
import com.example.travel_buddy.classes_res.Duration
import com.example.travel_buddy.classes_res.Travel_point
import com.example.travel_buddy.classes_res.dbTravel_point
import com.example.travel_buddy.functions.formatLocation
import com.example.travel_buddy.functions.parseLocation

@Entity(tableName = "attraction_points")
data class dbAttraction_point(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "No name point",
    val date: String = "day/month/year hour:minute",
    val end_date: String = "day/month/year hour:minute",
    var time: String = "days, hours:minutes",
    var location: String = "",
    var newId: Int = 0,
    var travel_plan_name: String = "No travel plan name",
    var city: String = "",
    var neighborhood: String = "",
    var phone: String = "",
    var freeFormAddress: String = "",
    var meetingPoint: String = "",
    var notes: String = ""
)

fun dbAttraction_point.translateFromDb(): Attraction_point {
    return Attraction_point(
        name = this.name,
        date = Date(this.date),
        end_date = Date(this.end_date),
        time = Date(this.date) - Date(this.end_date),
        location = parseLocation(this.location),
        newId = this.newId,
        travel_plan_name = this.travel_plan_name,
        city = this.city,
        neighborhood = this.neighborhood,
        phone = this.phone,
        freeFormAddress = this.freeFormAddress,
        meetingPoint = this.meetingPoint,
        notes =this.notes
    )
}

class Attraction_point(
    name: String = "No name point",
    date: Date = Date(),
    end_date: Date = date,
    time: Duration = end_date - date,
    location: Location? = null,
    newId: Int = 0, // Nowe pole ID
    travel_plan_name: String = "No travel plan name",
    var city: String = "",
    var neighborhood: String = "",
    var phone: String = "",
    var freeFormAddress: String = "",
    var meetingPoint: String = "",
    notes: String = ""
) : Travel_point(name, date,end_date, time,  location, notes, newId, travel_plan_name) {

    fun getDbObject(): dbAttraction_point {
        return dbAttraction_point(
            name = name,
            date = date.toString(),
            end_date = end_date.toString(),
            time = time.toString(),
            location = location.toString(),
            newId = this.newId,
            travel_plan_name = this.travel_plan_name,
            city = this.city,
            neighborhood = this.neighborhood,
            phone = this.phone,
            freeFormAddress = this.freeFormAddress,
            meetingPoint = this.meetingPoint,
            notes = this.notes
        )
    }

    override fun ToDb(): dbAttraction_point {
        return dbAttraction_point(
            name = this.name,
            date = this.date.toString(),
            end_date = this.end_date.toString(),
            time = this.time.toString(),
            location = (this.location?.let { formatLocation(it) } ?: ""),
            newId = this.newId,
            travel_plan_name = this.travel_plan_name,
            city = this.city,
            neighborhood = this.neighborhood,
            phone = this.phone,
            freeFormAddress = this.freeFormAddress,
            meetingPoint = this.meetingPoint,
            notes = this.notes
        )
    }

    override fun toString(): String {
        return "Name: $name, Date: $date, End activity date: $end_date, $location, Time: $time, ID: $newId, Travel Plan: $travel_plan_name"
    }
}
