package com.example.travel_buddy.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travel_buddy.classes_res.dbTravel_point
import com.example.travel_buddy.data.TravelPlannerDatabase
import com.example.travel_buddy.data.TravelPointDao
import com.example.travel_buddy.data.TravelPointEvent
import com.example.travel_buddy.data.TravelPointRepository
import com.example.travel_buddy.data.TravelPointState
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

    fun insert(travelPoint: dbTravel_point) = viewModelScope.launch {
        repository.insert(travelPoint)
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
