package com.example.travel_buddy.classes_res.heritage_points

import Date
import android.location.Location
import com.example.travel_buddy.classes_res.Travel_point

class Hotel_point(
    name: String = "No name point",
    val city: String? = "",
    date: Date = Date(),
    val end_date: Date = Date(),
    location: Location? = null
) : Travel_point (name, date, location)
{
    override fun toString(): String {
        return "Name: $name, Date: $date, End reservation date: $end_date, $location, City: $city,"
    }
}