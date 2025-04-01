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
import com.example.travel_buddy.classes_res.heritage_points.TravelPointWithTrips
import com.example.travel_buddy.classes_res.heritage_points.Trip_point
import com.example.travel_buddy.classes_res.heritage_points.dbAttraction_point
import com.example.travel_buddy.classes_res.heritage_points.dbTrip_point
import com.example.travel_buddy.data.TravelPlannerDatabase
import com.example.travel_buddy.data.TravelPointDao
import com.example.travel_buddy.data.TravelPointRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DataEntryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TravelPointRepository
    val allTravelPoints: LiveData<List<dbTravel_point>>

    init {
        val dao = TravelPlannerDatabase.getDatabase(application).travelPointDao()
        repository = TravelPointRepository(dao)
        allTravelPoints = repository.allTravelPoints
    }

    fun getTravelPointWithTrips(travelPointId: Int): LiveData<TravelPointWithTrips> {
        return repository.getTravelPointWithTrips(travelPointId)
    }

    /*

    fun getAllTripsForId(travelPointId: Int): LiveData<List<dbTrip_point>> {
        return repository.getTripsForId(travelPointId)
    }

     */

    fun insertTravelPoint(travelPoint: Travel_point) = viewModelScope.launch {
        repository.insert(travelPoint)
    }

    fun insertTripPoint(tripPoint: Trip_point) = viewModelScope.launch {
        repository.insertTrip(tripPoint)
    }

    fun insertHotelPoint(hotelPoint: Hotel_point) = viewModelScope.launch {
        repository.insertHotel(hotelPoint)
    }

    fun insertAttractionPoint(attractionPoint: Attraction_point) = viewModelScope.launch {
        repository.insertAttraction(attractionPoint)
    }

    fun deleteById(id: Int) = viewModelScope.launch {
        repository.deleteById(id)
    }
}

    /*
    suspend fun saveItem() {
        travelPointDao.insertItem(travelpoint)
    }

     */
