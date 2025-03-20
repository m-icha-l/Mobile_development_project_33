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
