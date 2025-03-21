package com.example.travel_buddy.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.travel_buddy.classes_res.Travel_point
import kotlinx.coroutines.flow.Flow

@Dao
interface TravelPointDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Travel_point)

    @Query("SELECT * from travel_points ORDER BY date ASC")
    fun getAllItems(): Flow<List<Travel_point>>


}