package com.example.travel_buddy

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travel_buddy.classes_res.Travel_point
import com.example.travel_buddy.classes_res.heritage_points.Attraction_point
import com.example.travel_buddy.classes_res.heritage_points.Hotel_point
import com.example.travel_buddy.classes_res.heritage_points.Trip_point
import com.example.travel_buddy.functions.exportToPdf
import com.example.travel_buddy.viewmodel.DataEntryViewModel

@Composable
fun TestDisplay(name: String, dataEntryViewModel: DataEntryViewModel = viewModel(), modifier: Modifier = Modifier) {

    val travelPoints by dataEntryViewModel.allTravelPoints.observeAsState(initial = emptyList())
    val travelPointWithTrips by dataEntryViewModel.getTravelPointWithTrips(1).observeAsState()

    dataEntryViewModel.insertTravelPoint(Travel_point(name = "New Place"))

    val test_travel = Travel_point("travel class")
    val test_attraction = Attraction_point(1,"santa claus post office")
    val test_hotel = Hotel_point(1,"rovaniemi hotel")
    val test_trip = Trip_point(1,"santa claus village")
    val test_trip2 = Trip_point(1,"santa claus village2")

    //This is only for testing, never insert objects into database directly in @Composable or LaunchedEffect, because any recomposition with observeAsState() will lead to infinite insert loop
    //Insert functions should be called only on user actions, for example buttons

    dataEntryViewModel.insertTravelPoint(test_travel)
    dataEntryViewModel.insertTripPoint(test_trip)
    dataEntryViewModel.insertTripPoint(test_trip2)
    dataEntryViewModel.insertHotelPoint(test_hotel)
    dataEntryViewModel.insertAttractionPoint(test_attraction)

    val test = Travel_point()
    val test2 = Travel_point()
    val test3 = Travel_point()
    val travel_points = arrayOf(test,test2,test3)
    val context = LocalContext.current
    exportToPdf(context,"testpdf",travel_points)

    Travel_Point_Manager.add_Point("Vacation", Hotel_point(name = "Hotel_1"))
    Travel_Point_Manager.add_Point("Vacation", Travel_point(name = "Mountain"))
    Travel_Point_Manager.add_Point("Business", Attraction_point(name = "Conference"))
    Column {
        Text(
            text = "Hello $name! \n $test_travel \n $test_attraction \n $test_hotel \n $test_trip",
            modifier = modifier
        )
        Text(
            text = "$Travel_Point_Manager",
            modifier = modifier
        )
    }

}