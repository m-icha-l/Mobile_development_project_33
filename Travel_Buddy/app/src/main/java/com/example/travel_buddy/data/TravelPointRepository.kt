package com.example.travel_buddy.data

import androidx.lifecycle.LiveData
import com.example.travel_buddy.classes_res.Travel_point
import com.example.travel_buddy.classes_res.dbTravel_point
import com.example.travel_buddy.classes_res.heritage_points.Attraction_point
import com.example.travel_buddy.classes_res.heritage_points.Hotel_point
import com.example.travel_buddy.classes_res.heritage_points.Trip_point
import com.example.travel_buddy.classes_res.heritage_points.dbAttraction_point
import com.example.travel_buddy.classes_res.heritage_points.dbHotel_point
import com.example.travel_buddy.classes_res.heritage_points.dbTrip_point

class TravelPointRepository(private val travelPointDao: TravelPointDao) {

    val allTravelPoints: LiveData<List<dbTravel_point>> = travelPointDao.getAllItems()

    suspend fun insert(dbTravelPoint: Travel_point) {
        travelPointDao.insert(dbTravelPoint.getDb())
    }

    suspend fun insertTrip(rootTravelPoint: Travel_point, tripPoint: Trip_point) {
        val dbTripPoint: dbTrip_point = tripPoint.getDbObject(rootTravelPoint.getDb().id)
        travelPointDao.insertTrip(dbTripPoint)
    }

    suspend fun insertHotel(rootTravelPoint: Travel_point, hotelPoint: Hotel_point) {
        val dbHotelPoint: dbHotel_point = hotelPoint.getDbObject(rootTravelPoint.getDb().id)
        travelPointDao.insertHotel(dbHotelPoint)
    }

    suspend fun insertAttraction(rootTravelPoint: Travel_point, attractionPoint: Attraction_point) {
        val dbAttractionPoint: dbAttraction_point = attractionPoint.getDbObject(rootTravelPoint.getDb().id)
        travelPointDao.insertAttraction(dbAttractionPoint)
    }

    suspend fun deleteById(id: Int) {
        travelPointDao.deleteItem(id)
    }

}