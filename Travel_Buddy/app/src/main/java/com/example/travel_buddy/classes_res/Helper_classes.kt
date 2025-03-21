import com.example.travel_buddy.classes_res.Travel_point
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.Hours
import org.joda.time.Minutes
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
        return "$days days, $hours hours, $minutes minutes"
    }
}

object Travel_Point_Manager {
    private val travelPointsMap: MutableMap<String, MutableList<Travel_point>> = mutableMapOf()

    // Add a new Travel_point to a trip_name
    fun add_Point(trip_name: String, point: Travel_point) {
        travelPointsMap.getOrPut(trip_name) { mutableListOf() }.add(point)
    }

    // Get a specific Travel_point by index
    fun get_point_from(trip_name: String, index: Int): Travel_point? {
        return travelPointsMap[trip_name]?.getOrNull(index)
    }

    // Replace the entire list of Travel_points for a trip_name
    fun replace_point_List(trip_name: String, newList: List<Travel_point>) {
        travelPointsMap[trip_name] = newList.toMutableList()
    }

    // Replace a specific Travel_point in a trip_name at a given index
    fun replace_point_from(trip_name: String, index: Int, newPoint: Travel_point): Boolean {
        val list = travelPointsMap[trip_name]
        return if (list != null && index in list.indices) {
            list[index] = newPoint
            true
        } else {
            false // Returns false if index is out of bounds
        }
    }

    // Display all categories with their travel points
    fun display_all_trips() : String {
        var str = ""
        if (travelPointsMap.isEmpty()) {
            str = "No travel points available"
        }
        for ((trip_name, points) in travelPointsMap) {
            str += "trip_name: $trip_name\n"
            points.forEachIndexed { index, point ->
                str += "  ${index + 1}. $point\n"
            }
            str += "\n"
        }
        return str
    }

    // Display all travel points under a specific trip_name
    fun display_trip(trip_name: String) : String {
        var str = ""
        val points = travelPointsMap[trip_name]
        if (points.isNullOrEmpty()) {
            str = "No travel points found in trip_name: $trip_name"
        } else {
            println("trip_name: $trip_name")
            points.forEachIndexed { index, point ->
                str += "  ${index + 1}. $point\n"
            }
        }
        return str
    }

    override fun toString(): String {
        return display_all_trips()
    }
}