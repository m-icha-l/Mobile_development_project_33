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
import android.util.Log
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.travel_buddy.classes_res.heritage_points.TravelPlanName
import com.example.travel_buddy.classes_res.heritage_points.TravelPointWithTrips

class TravelPointRepository(private val travelPointDao: TravelPointDao) {

    val allTravelPoints: LiveData<List<dbTravel_point>> = travelPointDao.getAllItems()

    fun getTravelPointWithTrips(travelPointId: Int): LiveData<TravelPointWithTrips> {
        return travelPointDao.getTravelPointWithTrips(travelPointId)
    }

    /*

    fun getTripsForId(travelPointId: Int): LiveData<List<dbTrip_point>> {
        return travelPointDao.getAllTripsForId(travelPointId)
    }

     */

    suspend fun insert(trip_name: String,travelPoint: dbTravel_point) {
        travelPoint.travel_plan_name = trip_name
        //val id = travelPointDao.insert(travelPoint.getDb())
        val id = travelPointDao.insert(travelPoint)
    }

    suspend fun update(trip_name: String,travelPoint: dbTravel_point) {
        travelPoint.travel_plan_name = trip_name
        travelPointDao.update(dbTravel_point())
    }

    suspend fun insertName(item: TravelPlanName) {
        travelPointDao.insertName(item)
    }

    fun getAllNames(): List<TravelPlanName> {
        return travelPointDao.getAllNames()
    }

    fun getAllTravelsFromPlan(travel_plan_name: String) : MutableList<dbTravel_point> {
        return travelPointDao.getAllTravelsFromPlan(travel_plan_name)
    }

    suspend fun insertTrip(tripPoint: Trip_point) {
        //Log.d("TRAVEL POINT NAME",rootTravelPoint.getDb().name)
        val dbTripPoint: dbTrip_point = tripPoint.getDbObject()
        travelPointDao.insertTrip(dbTripPoint)
    }

    suspend fun insertHotel(hotelPoint: Hotel_point) {
        val dbHotelPoint: dbHotel_point = hotelPoint.getDbObject()
        travelPointDao.insertHotel(dbHotelPoint)
    }

    suspend fun insertAttraction(attractionPoint: Attraction_point) {
        val dbAttractionPoint: dbAttraction_point = attractionPoint.getDbObject()
        travelPointDao.insertAttraction(dbAttractionPoint)
    }

    suspend fun deleteById(id: Int) {
        travelPointDao.deleteItem(id)
    }

}