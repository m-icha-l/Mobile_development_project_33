package com.example.travel_buddy.data

import com.example.travel_buddy.classes_res.dbTravel_point

interface TravelPointEvent {
    object SaveTravelPoint: TravelPointEvent
    object GetAllPoints: TravelPointEvent
    data class SetName(val name: String): TravelPointEvent
    data class SetDate(val date: String): TravelPointEvent
    data class SetLocation(val location: String): TravelPointEvent
    data class DeletePoint(val travelPoint: dbTravel_point): TravelPointEvent
}