package com.example.travel_buddy.classes_res.heritage_points

import com.example.travel_buddy.classes_res.Date
import com.example.travel_buddy.classes_res.Duration
import android.location.Location
import android.util.Log
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.travel_buddy.classes_res.Travel_point
import com.example.travel_buddy.classes_res.dbTravel_point
import com.example.travel_buddy.functions.formatLocation
import com.example.travel_buddy.functions.parseLocation

@Entity(tableName = "trip_points")
data class dbTrip_point(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val newId: Int = 0,
    val name: String = "No name point",
    var end_location: String = "",
    val date: String = "day/month/year hour:minute",
    val end_date: String = "day/month/year hour:minute",
    val time: String = "days, hours:minutes",
    var location: String = "",
    var travel_plan_name: String = "No travel plan name",
    var notes: String,
    // NOWE
    var start_subdivision: String = "No subdivision",
    var start_country: String = "",
    var dest_name: String = "",
    var dest_subdivision: String = "No subdivision",
    var dest_country: String = "",
    var distance: String = "",
    var urlToPhoto: String = ""
)

fun dbTrip_point.translateFromDb(): Trip_point {
    return Trip_point(
        newId = this.newId,
        name = this.name,
        end_location = parseLocation(this.end_location),
        date = Date(this.date), // Tworzy obiekt klasy Date
        end_date = Date(this.end_date),
        time = Date(this.date) - Date(this.end_date),
        location = parseLocation(this.location),
        plan_name = this.travel_plan_name,
        notes = notes,
        start_subdivision = this.start_subdivision,
        start_country = this.start_country,
        dest_name = this.dest_name,
        dest_subdivision = this.dest_subdivision,
        dest_country = this.dest_country,
        distance = this.distance,
        urlToPhoto = this.urlToPhoto
    )
}

class Trip_point(
    newId: Int = 0,
    name: String = "No name point",
    var end_location: Location? = null,
    date: Date = Date(),
    end_date: Date = date,
    time: Duration = end_date - date,
    location: Location? = null,
    var plan_name: String = "It belongs to no name plan",
    notes: String = "",
    // NOWE
    var start_subdivision: String = "No subdivision",
    var start_country: String = "",
    var dest_name: String = "",
    var dest_subdivision: String = "No subdivision",
    var dest_country: String = "",
    var distance: String = "",
    var urlToPhoto: String = ""

) : Travel_point(name, date,end_date,time, location,notes) {
    init {
        if (end_location == null) {

            //location = get_current_location

            end_location = Location("default").apply { // placeholder waiting for Mikłaj's function
                latitude = 0.0
                longitude = 0.0
            }
        }
    }
    fun getDbObject(): dbTrip_point {
        Log.d("ROOT ID",newId.toString())
        return dbTrip_point(
            newId = newId,
            name = name,
            end_location = (this.end_location?.let { formatLocation(it) } ?: ""),
            date = date.toString(),
            end_date = end_date.toString(),
            time = time.toString(),
            location = (this.location?.let { formatLocation(it) } ?: ""),
            travel_plan_name = travel_plan_name,
            notes = notes,
            start_subdivision = start_subdivision,
            start_country = start_country,
            dest_name = dest_name,
            dest_subdivision = dest_subdivision,
            dest_country = dest_country,
            distance = distance,
            urlToPhoto = urlToPhoto
        )
    }
    override fun toString(): String {
        return "Name: $name, Date: $date, End reservation date: $end_date, $location, time: $time"
    }

    override fun ToDb(): dbTrip_point {
        return dbTrip_point(
            newId = this.newId,
            name = name,
            end_location = (this.end_location?.let { formatLocation(it) } ?: ""),
            date = this.date.toString(),
            end_date = this.end_date.toString(),
            time = this.time.toString(),
            location = (this.location?.let { formatLocation(it) } ?: ""),
            travel_plan_name = this.plan_name,
            notes = this.notes,
            start_subdivision = start_subdivision,
            start_country = start_country,
            dest_name = dest_name,
            dest_subdivision = dest_subdivision,
            dest_country = dest_country,
            distance = distance,
            urlToPhoto = urlToPhoto
        )
    }
}