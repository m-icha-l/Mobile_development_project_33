package com.example.travel_buddy.classes_res

import Date
import android.util.Log
import android.location.Location


open class Travel_point(
    val name: String = "No name point",
    val date: Date  = Date(), //in Helper_class
    var location: Location? = null
){
    init {
        if (location == null) {

            //location = get_current_location

            location = Location("default").apply { // placeholder waiting for Mikłaj's function
                latitude = 0.0
                longitude = 0.0
            }
        }
    }

    override fun toString(): String {
        return "Name: $name, Date: $date, $location"
    }
}