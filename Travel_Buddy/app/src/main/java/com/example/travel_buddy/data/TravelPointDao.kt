package com.example.travel_buddy.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.travel_buddy.classes_res.Travel_point
import com.example.travel_buddy.classes_res.dbTravel_point
import com.example.travel_buddy.classes_res.heritage_points.TravelPlanName
import com.example.travel_buddy.classes_res.heritage_points.dbAttraction_point
import com.example.travel_buddy.classes_res.heritage_points.dbHotel_point
import com.example.travel_buddy.classes_res.heritage_points.dbTrip_point
import kotlinx.coroutines.flow.Flow

@Dao
interface TravelPointDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: dbTravel_point): Long

    @Update
    suspend fun update(item: dbTravel_point)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertName(item: TravelPlanName): Long

    @Query("SELECT * from travel_plan_names")
    fun getAllNames(): List<TravelPlanName>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrip(item: dbTrip_point)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHotel(item: dbHotel_point)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAttraction(item: dbAttraction_point)

    @Query("SELECT * from travel_points ORDER BY date ASC")
    fun getAllItems(): LiveData<List<dbTravel_point>>

    @Query("SELECT * from travel_points WHERE travel_plan_name = :travel_plan_name ORDER BY date ASC")
    fun getAllTravelsFromPlan(travel_plan_name: String): MutableList<dbTravel_point>

    @Query("SELECT * from trip_points WHERE travel_plan_name = :travel_plan_name ORDER BY date ASC")
    fun getAllTripsFromPlan(travel_plan_name: String): MutableList<dbTrip_point>

    @Query("SELECT * from hotel_points WHERE travel_plan_name = :travel_plan_name ORDER BY date ASC")
    fun getAllHotelsFromPlan(travel_plan_name: String): MutableList<dbHotel_point>

    @Query("SELECT * from attraction_points WHERE travel_plan_name = :travel_plan_name ORDER BY date ASC")
    fun getAllAttractionsFromPlan(travel_plan_name: String): MutableList<dbAttraction_point>

    @Query("SELECT * from travel_points WHERE id = :id")
    fun getItem(id: Int): LiveData<dbTravel_point>

    @Query("DELETE FROM travel_points WHERE id = :travelPointId")
    suspend fun deleteItem(travelPointId: Int)

    /*

    @Query("SELECT * from trip_points WHERE id = :travelPointId ORDER BY date ASC")
    fun getAllTripsForId(travelPointId: Int): LiveData<List<dbTrip_point>>

    @Query("SELECT * from hotel_points WHERE id = :travelPointId ORDER BY date ASC")
    fun getAllHotelsForId(travelPointId: Int): LiveData<List<dbHotel_point>>

    @Query("SELECT * from attraction_points WHERE id = :travelPointId ORDER BY date ASC")
    fun getAllAttractionForId(travelPointId: Int): LiveData<List<dbAttraction_point>>

     */
}