package com.example.travel_buddy.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.travel_buddy.classes_res.dbTravel_point
import com.example.travel_buddy.classes_res.heritage_points.dbAttraction_point
import com.example.travel_buddy.classes_res.heritage_points.dbHotel_point
import com.example.travel_buddy.classes_res.heritage_points.dbTrip_point
import com.example.travel_buddy.data.TravelPointDao

@Database(entities = [dbTravel_point::class, dbTrip_point::class,dbHotel_point::class,dbAttraction_point::class], version = 1, exportSchema = false)
abstract class TravelPlannerDatabase : RoomDatabase() {
    abstract fun travelPointDao(): TravelPointDao
    companion object {
        @Volatile //The value of a volatile variable is never cached, and all reads and writes are to and from the main memory.
        // These features help ensure the value of Instance is always up to date and is the same for all execution threads.
        private var Instance: TravelPlannerDatabase? = null

        fun getDatabase(context: Context): TravelPlannerDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, TravelPlannerDatabase::class.java, "travel_planner_database")
                    //.fallbackToDestructiveMigration() //delete and re-initialize the database on parameter changes in the class
                    .build()
                    .also { Instance = it }
            }
        }

    }
}