package com.example.travel_buddy.classes_res.heritage_points

import Date
import Duration
import android.location.Location
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.travel_buddy.classes_res.Travel_point
import com.example.travel_buddy.classes_res.dbTravel_point
import com.example.travel_buddy.functions.formatLocation
import com.example.travel_buddy.functions.parseLocation

@Entity(tableName = "attraction_points")
data class dbAttraction_point(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val rootId: Int = 0,
    val name: String = "No name point",
    val date: String = "day/month/year hour:minute",
    val end_date: String = "day/month/year hour:minute",
    var time: String = "hour:minute",
    var location: String = "",
    var newId: Int = 0, // Nowe pole ID
    var travel_plan_name: String = "No travel plan name" // Nowe pole travel_plan_name
)

fun dbAttraction_point.translateFromDb(): Attraction_point {
    return Attraction_point(
        rootId = this.rootId,
        name = this.name,
        date = Date(this.date), // Tworzy obiekt klasy Date
        end_date = Date(this.end_date),
        time = Date(this.date) - Date(this.end_date), // Tworzy obiekt klasy Duration
        location = parseLocation(this.location), // Tworzy obiekt android.location.Location
        newId = this.newId, // Przekazanie ID
        travel_plan_name = this.travel_plan_name // Przekazanie travel_plan_name
    )
}

class Attraction_point(
    var rootId: Int = 0,
    name: String = "No name point",
    date: Date = Date(),
    var end_date: Date = Date(),
    var time: Duration = date - end_date,
    location: Location? = null,
    newId: Int = 0, // Nowe pole ID
    travel_plan_name: String = "No travel plan name" // Nowe pole travel_plan_name
) : Travel_point(name, date, location, newId, travel_plan_name) { // Przekazywanie ID i travel_plan_name do Travel_point

    fun getDbObject(): dbAttraction_point {
        return dbAttraction_point(
            rootId = rootId,
            name = name,
            date = date.toString(),
            end_date = end_date.toString(),
            time = time.toString(),
            location = location.toString(),
            newId = this.newId, // Przekazanie ID
            travel_plan_name = this.travel_plan_name // Przekazanie travel_plan_name
        )
    }

    override fun ToDb(): dbAttraction_point {
        return dbAttraction_point(
            rootId = this.rootId,
            name = this.name,
            date = this.date.toString(), // Klasa Date ma nadpisane toString()
            end_date = this.end_date.toString(),
            time = this.time.toString(),
            location = (this.location?.let { formatLocation(it) } ?: ""),
            newId = this.newId, // Przekazanie ID
            travel_plan_name = this.travel_plan_name // Przekazanie travel_plan_name
        )
    }

    override fun toString(): String {
        return "Name: $name, Date: $date, End activity date: $end_date, $location, Time: $time, ID: $newId, Travel Plan: $travel_plan_name"
    }
}
