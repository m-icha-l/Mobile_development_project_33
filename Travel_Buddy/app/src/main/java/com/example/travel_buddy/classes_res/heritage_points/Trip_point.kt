package com.example.travel_buddy.classes_res.heritage_points

import Date
import Duration
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

@Entity(tableName = "trip_points")
data class dbTrip_point(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val rootId: Int = 0,
    val name: String = "No name point",
    var end_location: String = "",
    val date: String = "day/month/year hour:minute",
    val end_date: String = "day/month/year hour:minute",
    val time: String = "hour:minute",
    var location: String = ""
)

class Trip_point(
    var rootId: Int = 0, // <--- TO JEST DO WYJEBANIA
    name: String = "No name point",
    var end_location: Location? = null,
    date: Date = Date(),
    var end_date: Date = Date(),
    var time: Duration = date - end_date,
    location: Location? = null,
    val ID: Int = 0,
    val plan_name: String = "It belongs to no name plan"

) : Travel_point(name, date, location) {
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
        Log.d("ROOT ID",rootId.toString())
        return dbTrip_point(rootId = rootId,name = name, end_location = end_location.toString(),
            date = date.toString(),end_date = end_date.toString(),time = time.toString(), location = location.toString())
    }
    override fun toString(): String {
        return "Name: $name, Date: $date, End reservation date: $end_date, $location, time: $time"
    }
}