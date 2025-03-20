package com.example.travel_buddy.classes_res.heritage_points

import Date
import Duration
import android.location.Location
import com.example.travel_buddy.classes_res.Travel_point

class Trip_point(
    name: String = "No name point",
    var end_location: Location? = null,
    date: Date = Date(),
    var end_date: Date = Date(),
    var time: Duration = date - end_date,
    location: Location? = null
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
    override fun toString(): String {
        return "Name: $name, Date: $date, End reservation date: $end_date, $location, time: $time"
    }
}