package com.example.travel_buddy.classes_res.heritage_points

import Date
import android.location.Location
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.travel_buddy.classes_res.Travel_point

@Entity(tableName = "hotel_points")
data class dbHotel_point(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val rootId: Int = 0,
    val name: String = "No name point",
    val city: String? = "",
    val date: String = "day/month/year hour:minute",
    val end_date: String = "day/month/year hour:minute",
    var location: String = ""
)

class Hotel_point(
    name: String = "No name point",
    val city: String? = "",
    date: Date = Date(),
    val end_date: Date = Date(),
    location: Location? = null
) : Travel_point(name, date, location)
{
    fun getDbObject(rootId:Int): dbHotel_point {
        return dbHotel_point(
            rootId = rootId,
            name = name,
            city = city,
            date = date.toString(),
            end_date = end_date.toString(),
            location = location.toString())
    }

    override fun toString(): String {
        return "Name: $name, Date: $date, End reservation date: $end_date, $location, City: $city,"
    }
}