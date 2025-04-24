package com.example.travel_buddy.classes_res

import com.example.travel_buddy.classes_res.heritage_points.Attraction_point
import com.example.travel_buddy.classes_res.heritage_points.Hotel_point
import com.example.travel_buddy.classes_res.heritage_points.TravelPlanName
import com.example.travel_buddy.classes_res.heritage_points.Trip_point
import com.example.travel_buddy.classes_res.heritage_points.translateFromDb
import com.example.travel_buddy.viewmodel.DataEntryViewModel
import android.util.Log
import org.joda.time.DateTime
import org.joda.time.Period

class Date(dateString: String? = null) {
    var day: Int
    var month: Int
    var year: Int
    var hour: Int
    var minute: Int
    var dateTime: DateTime

    init {
        val format = org.joda.time.format.DateTimeFormat.forPattern("dd/MM/yyyy HH:mm")

        // If dateString is not provided, it will take the current date and time
        dateTime = if (dateString != null) {
            DateTime.parse(dateString, format)
        } else {
            DateTime.now() // current date and time
        }

        day = dateTime.dayOfMonth
        month = dateTime.monthOfYear
        year = dateTime.year
        hour = dateTime.hourOfDay
        minute = dateTime.minuteOfHour
    }

    // Operator Overloading to subtract Dates using Joda-Time
    operator fun minus(other: Date): Duration {
        // Calculate the difference between two DateTime objects
        val period = Period(other.dateTime, this.dateTime)

        // Return a custom Duration object
        return Duration(
            days = period.days.toLong(),
            hours = period.hours.toLong(),
            minutes = period.minutes.toLong()
        )
    }

    override fun toString(): String {
        return "$day/$month/$year $hour:$minute"
    }
}

data class Duration(
    var days: Long = 0,
    var hours: Long = 0,
    var minutes: Long = 0
){
    override fun toString(): String {
        return "$days, $hours:$minutes"
    }
}

data class Trip(
    var trip_name: String = "",
    var points_list: MutableList<Travel_point>
)

//          MIKOLAJ PLS MAPA MA PRZECHOWYWAC TRAVEL_POINT ZEBY FUNKCJE DZIALALY DO KAZDEJ KLASY DOPISALEM CI FUNKCJE DO
//          TRANSLACJI MIEDZY DBTRAVEL_POINT A TRAVEL_POINT(SA IMPLEMENTOWANE DLA KAZEDJ PODKLASY TRAVEL POINT ZOBACZ KOD)


class Travel_Point_Manager(val dataEntryViewModel: DataEntryViewModel) {

    private val travelPointsMap: MutableMap<String, MutableList<Travel_point>> = mutableMapOf()
    private val lastIndexMap: MutableMap<String, Int> = mutableMapOf()

    init {
        val namesList = dataEntryViewModel.getAllNames()
        for (list_name in namesList) {
            var travels = dataEntryViewModel.getAllTravelsFromPlan(list_name.name)
            var trips = dataEntryViewModel.getAllTripsFromPlan(list_name.name)
            var hotels = dataEntryViewModel.getAllHotelsFromPlan(list_name.name)
            var attractions = dataEntryViewModel.getAllAttractionsFromPlan(list_name.name)

            val travelsList: MutableList<Travel_point> = mutableListOf()
            for(travel in travels) {
                travelsList.add(travel.translateFromDb())
            }
            for(trip in trips) {
                travelsList.add(trip.translateFromDb())
            }
            for(hotel in hotels) {
                travelsList.add(hotel.translateFromDb())
            }
            for(attraction in attractions) {
                travelsList.add(attraction.translateFromDb())
            }
            travelsList.sortBy { it.newId }
            travelPointsMap.put(list_name.name,travelsList)
            lastIndexMap.put(list_name.name,travelsList.lastIndex+1)
            Log.d("LAST INDEX",(travelsList.lastIndex+1).toString())
        }
    }


    // Add a new Travel_point to a trip_name
    fun add_Point(trip_name: String, point: Travel_point) {
        val nextIndex: Int
        if(!lastIndexMap.containsKey(trip_name)) {
            nextIndex = 1
            lastIndexMap.put(key = trip_name,value = nextIndex)
            dataEntryViewModel.insertName(TravelPlanName(name=trip_name))
        }
        else
        {
            nextIndex = lastIndexMap[trip_name]!! + 1
            lastIndexMap[trip_name] = nextIndex
        }
        travelPointsMap.getOrPut(trip_name) { mutableListOf() }.add(point)

        insertAnyPoint(nextIndex,trip_name,point)
    }

    // Get a specific Travel_point by index
    fun get_point_from(trip_name: String, index: Int): Travel_point? {
        return travelPointsMap[trip_name]?.getOrNull(index)
    }
    // Replace the entire list of Travel_points for a trip_name

    fun replace_point_List(trip_name: String, newList: List<Travel_point>) {
        deleteTravelPlan(trip_name,false)
        var currIndex = 1
        for(element in newList) {
            insertAnyPoint(currIndex,trip_name,element)
            currIndex++
        }

        lastIndexMap[trip_name] = currIndex
        travelPointsMap[trip_name] = newList.toMutableList()
    }
    // Replace a specific Travel_point in a trip_name at a given index
    fun replace_point_from(trip_name: String, index: Int, newPoint: Travel_point): Boolean {
        val list = travelPointsMap[trip_name]

        deleteAnyPoint(index,trip_name)

        return if (list != null && index in list.indices) {

            insertAnyPoint(index,trip_name,newPoint)

            list[index] = newPoint
            lastIndexMap[trip_name] = lastIndexMap[trip_name]!! + 1
            true

        } else
        {
            false // Returns false if index is out of bounds
        }

    }

    fun deleteTravelPlan(trip_name: String, deletePlanNames: Boolean) {
        if(deletePlanNames) {
            travelPointsMap.remove(trip_name)
            lastIndexMap.remove(trip_name)
        }
        dataEntryViewModel.deletePlan(trip_name)
    }

    // Display all categories with their travel points
    fun display_all_trips() : MutableList<Trip>? {

        var str = ""
        var _tripName: String = ""
        var _pointsList: MutableList<Travel_point> = mutableListOf()
        var tripArray = mutableListOf<Trip>()
        if (travelPointsMap.isEmpty()) {

            str = "No travel plans available"
            return null
        }

        for ((trip_name, points) in travelPointsMap) {

            str += "trip_name: $trip_name\n"
            _tripName = trip_name

            points.forEachIndexed { index, point ->
                str += "  ${index + 1}. $point\n"
                _pointsList.add(point)
            }
            tripArray.add(Trip(_tripName, _pointsList))
            str += "\n"
        }

        return tripArray
    }
    // Display all travel points under a specific trip_name
    fun display_trip(trip_name: String): Trip? {
        var str = ""
        var _tripName: String = ""
        var _pointsList: MutableList<Travel_point> = mutableListOf()
        val points = travelPointsMap[trip_name]

        if (points.isNullOrEmpty()) {
            str = "No travel points found in trip_name: $trip_name"
            return null

        } else {
            println("trip_name: $trip_name")

            points.forEachIndexed { index, point ->
                str += "  ${index + 1}. $point\n"
                _pointsList.add(point)
            }
        }
        return Trip(_tripName, _pointsList)
    }

    // Display one travel point under a specific trip_name
    fun display_trip_point(trip_name: String, point_id: Int?): Travel_point? {
        val points = travelPointsMap[trip_name]

        if (points.isNullOrEmpty() || point_id == null) {
            Log.d("display_trip_point", "null")
            return null
        } else {
            Log.d("display_trip_point", "trip_name: $trip_name")

            points.forEachIndexed { index, point ->
                if(point.newId == point_id)
                {
                    Log.d("display_trip_point", "Found good ID")
                    Log.d("display_trip_point", "${point.name} ${point.newId}")

                    return point
                }
            }
        }
        return null
    }

    fun insertAnyPoint(index: Int, trip_name: String,travelPoint: Travel_point) {
        when (travelPoint) {
            is Hotel_point -> {
                dataEntryViewModel.insertHotelPoint(index,trip_name,travelPoint)
            }
            is Trip_point -> {
                dataEntryViewModel.insertTripPoint(index,trip_name,travelPoint)
            }
            is Attraction_point -> {
                dataEntryViewModel.insertAttractionPoint(index,trip_name,travelPoint)
            }
            else -> {
                dataEntryViewModel.insert(index,trip_name,travelPoint)
            }
        }
    }

    fun deleteAnyPoint(index: Int, trip_name: String) {

        travelPointsMap[trip_name]?.removeAt(index)
        lastIndexMap[trip_name] = lastIndexMap[trip_name]!! - 1

        dataEntryViewModel.deleteTravelPoint(trip_name,index)
        dataEntryViewModel.deleteTripPoint(trip_name,index)
        dataEntryViewModel.deleteHotelPoint(trip_name,index)
        dataEntryViewModel.deleteAttractionPoint(trip_name,index)
    }

    fun get_trip_duration(trip_name: String): Int {
        val points = travelPointsMap[trip_name]
        var duration = 0

        if (points.isNullOrEmpty()) {

            duration = 0

        } else {
            println("trip_name: $trip_name")

            points.forEachIndexed{ index, point ->
                duration = (duration + (point.time?.days ?: 0 )).toInt()
            }
        }
        return duration
    }

/*    override fun toString(): String {
        return display_all_trips()
    }*/
}