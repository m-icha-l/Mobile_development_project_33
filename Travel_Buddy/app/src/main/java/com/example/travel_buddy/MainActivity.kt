package com.example.travel_buddy

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travel_buddy.classes_res.Travel_point
import com.example.travel_buddy.classes_res.dbTravel_point


import com.example.travel_buddy.classes_res.heritage_points.Attraction_point
import com.example.travel_buddy.classes_res.heritage_points.Hotel_point
import com.example.travel_buddy.classes_res.heritage_points.Trip_point

import com.example.travel_buddy.functions.exportToPdf

import com.example.travel_buddy.ui.theme.Travel_BuddyTheme
import com.example.travel_buddy.viewmodel.DataEntryViewModel
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

        val newTravelPoint = dbTravel_point(name = "Santa Claus Village", date = "23/03/2025 15:30", location = "Rovaniemi")
        travelPointViewModel.insert(newTravelPoint)
    }
}

@Composable
fun Greeting(name: String, dataEntryViewModel: DataEntryViewModel = viewModel(), modifier: Modifier = Modifier) {

    val travelPoints by dataEntryViewModel.allTravelPoints.observeAsState(initial = emptyList())

    dataEntryViewModel.insert(dbTravel_point(name = "New Place", location = "New York"))

    val test_travel = Travel_point("travel class")
    val test_attraction = Attraction_point("attraction class")
    val test_hotel = Hotel_point("hotel class")
    val test_trip = Trip_point("trip class")

    val test = Travel_point()
    val test2 = Travel_point()
    val test3 = Travel_point()
    val travel_points = arrayOf(test,test2,test3)
    val context = LocalContext.current
    exportToPdf(context,"testpdf",travel_points)

    val coroutineScope = rememberCoroutineScope()

    Text(
        text = "Hello $name! \n $test_travel \n $test_attraction \n $test_hotel \n $test_trip",
        modifier = modifier
    )
}