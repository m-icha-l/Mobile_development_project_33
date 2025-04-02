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
    val city: String = "", //Czemu to było nullable? Usunąłem to bo byłyby problemy przez to
    val date: String = "day/month/year hour:minute",
    val end_date: String = "day/month/year hour:minute",
    var location: String = "",
    var newId: Int = 0,  // Nowe pole ID
    var travel_plan_name: String = "No travel plan name" // Nowe pole travel_plan_name
)

fun dbHotel_point.translateFromDb(): Hotel_point {
    return Hotel_point(
        name = this.name,
        city = this.city,
        date = Date(this.date), // Tworzy obiekt klasy Date
        end_date = Date(this.end_date),
        location = parseLocation(this.location), // Tworzy obiekt android.location.Location
        newId = this.newId, // Przekazanie ID
        travel_plan_name = this.travel_plan_name // Przekazanie travel_plan_name
    )
}

class Hotel_point(
    name: String = "No name point",
    val city: String = "",
    date: Date = Date(),
    val end_date: Date = Date(),
    location: Location? = null,
    newId: Int = 0,  // Nowe pole ID
    travel_plan_name: String = "No travel plan name" // Nowe pole travel_plan_name
) : Travel_point(name, date, location, newId, travel_plan_name) { // Przekazywanie ID i travel_plan_name do Travel_point

    fun getDbObject(): dbHotel_point {
        return dbHotel_point(
            name = name,
            city = city,
            date = date.toString(),
            end_date = end_date.toString(),
            location = location.toString(),
            newId = this.newId,  // Przekazanie ID
            travel_plan_name = this.travel_plan_name  // Przekazanie travel_plan_name
        )
    }

    override fun toString(): String {
        return "Name: $name, Date: $date, End reservation date: $end_date, $location, City: $city, ID: $newId, Travel Plan: $travel_plan_name"
    }

    override fun ToDb(): dbHotel_point {
        return dbHotel_point(
            name = this.name,
            city = this.city,
            date = this.date.toString(), // Klasa Date ma nadpisane toString()
            end_date = this.end_date.toString(),
            location = (this.location?.let { formatLocation(it) } ?: ""),
            newId = this.newId,  // Przekazanie ID
            travel_plan_name = this.travel_plan_name  // Przekazanie travel_plan_name
        )
    }
}
