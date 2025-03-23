package com.example.travel_buddy.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.travel_buddy.classes_res.Travel_point
import com.example.travel_buddy.classes_res.dbTravel_point
import kotlinx.coroutines.flow.Flow

@Dao
interface TravelPointDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: dbTravel_point)

    @Query("SELECT * from travel_points ORDER BY date ASC")
    fun getAllItems(): LiveData<List<dbTravel_point>>

    @Query("SELECT * from travel_points WHERE id = :id")
    fun getItem(id: Int): LiveData<dbTravel_point>

    @Query("DELETE FROM travel_points WHERE id = :travelPointId")
    suspend fun deleteItem(travelPointId: Int)



}