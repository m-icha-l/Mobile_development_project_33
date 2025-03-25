package com.example.travel_buddy.classes_res.heritage_points

import Date
import android.location.Location
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.travel_buddy.classes_res.Travel_point
import com.example.travel_buddy.classes_res.dbTravel_point

@Entity(tableName = "hotel_points",foreignKeys = [ForeignKey(
    entity = dbTravel_point::class,
    parentColumns = ["id"],
    childColumns = ["rootId"],
    onDelete = ForeignKey.CASCADE  // Delete trips when parent is deleted
)],
    indices = [Index(value = ["rootId"])])
data class dbHotel_point(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val rootId: Int = 0,
    val name: String = "No name point",
    val city: String? = "",
    val date: String = "day/month/year hour:minute",
    val end_date: String = "day/month/year hour:minute",
    var location: String = ""
)

data class TravelPointWithHotels(
    @Embedded val travelPoint: dbTravel_point,
    @Relation(
        parentColumn = "id",
        entityColumn = "rootId"
    )
    val tripPoints: List<dbHotel_point>
)

class Hotel_point(
    var rootId: Int = 0,
    name: String = "No name point",
    val city: String? = "",
    date: Date = Date(),
    val end_date: Date = Date(),
    location: Location? = null
) : Travel_point(name, date, location)
{
    fun getDbObject(): dbHotel_point {
        return dbHotel_point(
            rootId = rootId,
            name = name,
            city = city,
            date = date.toString(),
            end_date = end_date.toString(),
            location = location.toString())
    }

    override fun toString(): String {
        return "Name: $name, Date: $date, End reservation date: $end_date, $location, City: $city,"
    }
}