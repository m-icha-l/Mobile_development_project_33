package com.example.travel_buddy.data

import androidx.lifecycle.LiveData
import com.example.travel_buddy.classes_res.dbTravel_point

class TravelPointRepository(private val travelPointDao: TravelPointDao) {

    val allTravelPoints: LiveData<List<dbTravel_point>> = travelPointDao.getAllItems()

    suspend fun insert(travelPoint: dbTravel_point) {
        travelPointDao.insert(travelPoint)
    }

    suspend fun deleteById(id: Int) {
        travelPointDao.deleteItem(id)
    }

}