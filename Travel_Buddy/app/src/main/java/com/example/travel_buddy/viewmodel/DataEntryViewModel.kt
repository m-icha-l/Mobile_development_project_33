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
import android.util.Log
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

class DataEntryViewModel(application: Application) : AndroidViewModel(application) {

    object TopBarName {
        private val _text = mutableStateOf("Travel Buddy")
        val text = _text.value
        fun updateText(newText: String) {
            _text.value = newText
            Log.d("text_update", "updatedText:  ${_text.value}")
        }
    }

    val allTravelPoints: LiveData<List<dbTravel_point>>
    //val allTravelPlanNames: LiveData<List<String>>

    private var travelPointDao: TravelPointDao

    init {
        travelPointDao = TravelPlannerDatabase.getDatabase(application).travelPointDao()
        allTravelPoints = travelPointDao.getAllItems()
    }

    fun getAllTravelsFromPlan(travel_plan_name: String): MutableList<dbTravel_point> {
        return travelPointDao.getAllTravelsFromPlan(travel_plan_name)
    }

    fun getAllTripsFromPlan(travel_plan_name: String): MutableList<dbTrip_point> {
        return travelPointDao.getAllTripsFromPlan(travel_plan_name)
    }

    fun getAllHotelsFromPlan(travel_plan_name: String): MutableList<dbHotel_point> {
        return travelPointDao.getAllHotelsFromPlan(travel_plan_name)
    }

    fun getAllAttractionsFromPlan(travel_plan_name: String): MutableList<dbAttraction_point> {
        return travelPointDao.getAllAttractionsFromPlan(travel_plan_name)
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

    fun insert(globalId:Int,trip_name: String,travelPoint: Travel_point) = viewModelScope.launch {
        travelPoint.travel_plan_name = trip_name
        travelPoint.newId = globalId
        val dbTravelPoint = travelPoint.getDb()
        travelPointDao.insert(dbTravelPoint)
    }

    fun update(trip_name: String,item: dbTravel_point) = viewModelScope.launch {
        item.travel_plan_name = trip_name
        travelPointDao.update(item)
    }

    fun insertTripPoint(globalId:Int,trip_name: String,tripPoint: Trip_point) = viewModelScope.launch {
        tripPoint.plan_name = trip_name
        tripPoint.newId = globalId
        val dbTripPoint: dbTrip_point = tripPoint.ToDb()
        travelPointDao.insertTrip(dbTripPoint)
    }

    fun insertHotelPoint(globalId:Int,trip_name: String,hotelPoint: Hotel_point) = viewModelScope.launch {
        hotelPoint.travel_plan_name = trip_name
        hotelPoint.newId = globalId
        val dbHotelPoint: dbHotel_point = hotelPoint.ToDb()
        val result = travelPointDao.insertHotel(dbHotelPoint)
        if (result == -1L) {
            Log.d("DAO", "Insert failed")
        } else {
            Log.d("DAO", "Insert successful, row ID: $result")
        }
    }

    fun insertAttractionPoint(globalId:Int,trip_name: String,attractionPoint: Attraction_point) = viewModelScope.launch {
        attractionPoint.travel_plan_name = trip_name
        attractionPoint.newId = globalId
        val dbAttractionPoint: dbAttraction_point = attractionPoint.ToDb()
        travelPointDao.insertAttraction(dbAttractionPoint)
    }

    fun deleteById(id: Int) = viewModelScope.launch {
        travelPointDao.deleteItem(id)
    }

    fun updateTravelPoint(trip_name: String, id: Int, travelPoint: Travel_point) = viewModelScope.launch {
        when(travelPoint) {
            is Travel_point -> {
                val dbTravelPoint = travelPoint.ToDb() as dbTravel_point
                travelPointDao.updateTravelPoint(dbTravelPoint.name,dbTravelPoint.date,dbTravelPoint.location,id,trip_name)
            }
            else -> {
                Log.d("ERROR", "Other object than Travel_point")
            }
        }
    }

    fun updateTripPoint(trip_name: String, id: Int, tripPoint: Trip_point) = viewModelScope.launch {
        val dbTrip_point = tripPoint.ToDb()
        travelPointDao.updateTripPoint(dbTrip_point.name,dbTrip_point.end_location,dbTrip_point.date,dbTrip_point.end_date,dbTrip_point.time,dbTrip_point.location,id,trip_name)
    }

    fun updateHotelPoint(trip_name: String, id: Int, hotelPoint: Hotel_point) = viewModelScope.launch {
        val dbHotel_point = hotelPoint.ToDb()
        travelPointDao.updateHotelPoint(dbHotel_point.name,dbHotel_point.city,dbHotel_point.date,dbHotel_point.end_date,dbHotel_point.location,id,trip_name)
    }

    fun updateAttractionPoint(trip_name: String, id: Int, attractionPoint: Attraction_point) = viewModelScope.launch {
        val dbAttraction_point = attractionPoint.ToDb()
        travelPointDao.updateAttractionPoint(dbAttraction_point.name,dbAttraction_point.date,dbAttraction_point.end_date,dbAttraction_point.time,dbAttraction_point.location,id,trip_name)
    }

    fun deletePlan(trip_name: String) = viewModelScope.launch {
        travelPointDao.deletePlanFromTravelPoints(trip_name)
        travelPointDao.deletePlanFromTripPoints(trip_name)
        travelPointDao.deletePlanFromHotelPoints(trip_name)
        travelPointDao.deletePlanFromAttractionPoints(trip_name)
    }

    fun deleteTravelPoint(trip_name: String, index: Int) {
        travelPointDao.deleteTravelPoint(trip_name,index)
    }

    fun deleteTripPoint(trip_name: String, index: Int) {
        travelPointDao.deleteTripPoint(trip_name,index)
    }

    fun deleteHotelPoint(trip_name: String, index: Int) {
        travelPointDao.deleteHotelPoint(trip_name,index)
    }

    fun deleteAttractionPoint(trip_name: String, index: Int) {
        travelPointDao.deleteAttractionPoint(trip_name,index)
    }
}

    /*
    suspend fun saveItem() {
        travelPointDao.insertItem(travelpoint)
    }

     */
