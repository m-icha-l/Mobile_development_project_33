package com.example.travel_buddy.classes_res.heritage_points

import Date
import Duration
import android.location.Location
import com.example.travel_buddy.classes_res.Travel_point

class Attraction_point(
    name: String = "No name point",
    date: Date = Date(),
    var end_date: Date = Date(),
    var time: Duration = date - end_date,
    location: Location? = null
) : Travel_point(name, date, location) {

    override fun toString(): String {
        return "Name: $name, Date: $date, End activity date: $end_date, $location, time: $time"
    }
}