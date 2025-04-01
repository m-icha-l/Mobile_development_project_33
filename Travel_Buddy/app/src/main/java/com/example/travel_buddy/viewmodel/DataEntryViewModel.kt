package com.example.travel_buddy.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travel_buddy.classes_res.Travel_point
import com.example.travel_buddy.classes_res.dbTravel_point
import com.example.travel_buddy.classes_res.heritage_points.Attraction_point
import com.example.travel_buddy.classes_res.heritage_points.Hotel_point
import com.example.travel_buddy.classes_res.heritage_points.TravelPlanName
import com.example.travel_buddy.classes_res.heritage_points.Trip_point
import com.example.travel_buddy.classes_res.heritage_points.dbAttraction_point
import com.example.travel_buddy.classes_res.heritage_points.dbHotel_point
import com.example.travel_buddy.classes_res.heritage_points.dbTrip_point
import com.example.travel_buddy.data.TravelPlannerDatabase
import com.example.travel_buddy.data.TravelPointDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DataEntryViewModel(application: Application, private var travelPointDao: TravelPointDao) : AndroidViewModel(application) {

    val allTravelPoints: LiveData<List<dbTravel_point>>
    //val allTravelPlanNames: LiveData<List<String>>

    init {
        travelPointDao = TravelPlannerDatabase.getDatabase(application).travelPointDao()
        allTravelPoints = travelPointDao.getAllItems()
    }

    fun getAllTravelsFromPlan(travel_plan_name: String): MutableList<dbTravel_point> {
        return travelPointDao.getAllTravelsFromPlan(travel_plan_name)
    }

    fun insertName(item: TravelPlanName) = viewModelScope.launch {
        travelPointDao.insertName(item)
    }

    fun getAllNames(): List<TravelPlanName> {
        return travelPointDao.getAllNames()
    }

    /*

    fun getAllTripsForId(travelPointId: Int): LiveData<List<dbTrip_point>> {
        return repository.getTripsForId(travelPointId)
    }

     */

    fun insert(trip_name: String,travelPoint: dbTravel_point) = viewModelScope.launch {
        travelPoint.travel_plan_name = trip_name
        travelPointDao.insert(travelPoint)
    }

    fun update(trip_name: String,item: dbTravel_point) = viewModelScope.launch {
        item.travel_plan_name = trip_name
        travelPointDao.update(item)
    }

    fun insertTripPoint(tripPoint: Trip_point) = viewModelScope.launch {
        val dbTripPoint: dbTrip_point = tripPoint.getDbObject()
        travelPointDao.insertTrip(dbTripPoint)
    }

    fun insertHotelPoint(hotelPoint: Hotel_point) = viewModelScope.launch {
        val dbHotelPoint: dbHotel_point = hotelPoint.getDbObject()
        travelPointDao.insertHotel(dbHotelPoint)
    }

    fun insertAttractionPoint(attractionPoint: Attraction_point) = viewModelScope.launch {
        val dbAttractionPoint: dbAttraction_point = attractionPoint.getDbObject()
        travelPointDao.insertAttraction(dbAttractionPoint)
    }

    fun deleteById(id: Int) = viewModelScope.launch {
        travelPointDao.deleteItem(id)
    }
}

    /*
    suspend fun saveItem() {
        travelPointDao.insertItem(travelpoint)
    }

     */
