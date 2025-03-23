package com.example.travel_buddy.data

import com.example.travel_buddy.classes_res.dbTravel_point

data class TravelPointState(
    val travel_points: List<dbTravel_point> = emptyList(),
    val name: String = "",
    val date: String = "day/month/year hour:minute",
    var location: String = ""
)