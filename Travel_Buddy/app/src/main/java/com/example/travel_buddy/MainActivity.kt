package com.example.travel_buddy

import Travel_Point_Manager
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travel_buddy.classes_res.Travel_point
import com.example.travel_buddy.classes_res.dbTravel_point
import androidx.compose.foundation.lazy.items


import com.example.travel_buddy.classes_res.heritage_points.Attraction_point
import com.example.travel_buddy.classes_res.heritage_points.Hotel_point
import com.example.travel_buddy.classes_res.heritage_points.Trip_point

import com.example.travel_buddy.functions.exportToPdf

import com.example.travel_buddy.ui.theme.Travel_BuddyTheme
import com.example.travel_buddy.viewmodel.DataEntryViewModel
import android.util.Log
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var travelPointViewModel: DataEntryViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        travelPointViewModel = ViewModelProvider(this).get(DataEntryViewModel::class.java)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Travel_BuddyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Travel buddy",
                        dataEntryViewModel = travelPointViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
        travelPointViewModel = ViewModelProvider(this).get(DataEntryViewModel::class.java)

        travelPointViewModel.allTravelPoints.observe(this, { travelPoints ->
            // Update UI with list of travel points
        })

        val newTravelPoint = Travel_point(name = "Santa Claus Village")
        travelPointViewModel.insertTravelPoint(newTravelPoint)
    }
}

@Composable
fun Greeting(name: String, dataEntryViewModel: DataEntryViewModel = viewModel(), modifier: Modifier = Modifier) {

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

    val test = Travel_point()
    val test2 = Travel_point()
    val test3 = Travel_point()
    val travel_points = arrayOf(test,test2,test3)
    val context = LocalContext.current
    exportToPdf(context,"testpdf",travel_points)


    Text(
        text = "Hello $name! \n $test_travel \n $test_attraction \n $test_hotel \n $test_trip",
        modifier = modifier
    )
}