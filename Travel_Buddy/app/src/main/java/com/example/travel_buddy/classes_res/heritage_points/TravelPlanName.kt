package com.example.travel_buddy.classes_res.heritage_points

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "travel_plan_names")
data class TravelPlanName(
    @PrimaryKey(autoGenerate = true)
    val name_id: Int = 0,
    val name: String
)
