package com.example.travel_buddy

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.travel_buddy.classes_res.Travel_point


import com.example.travel_buddy.classes_res.heritage_points.Attraction_point
import com.example.travel_buddy.classes_res.heritage_points.Hotel_point
import com.example.travel_buddy.classes_res.heritage_points.Trip_point

import com.example.travel_buddy.functions.exportToPdf

import com.example.travel_buddy.ui.theme.Travel_BuddyTheme
import com.example.travel_buddy.viewmodel.DataEntryViewModel
import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.stringResource
import com.example.travel_buddy.classes_res.Travel_Point_Manager
import com.example.travel_buddy.viewmodel.NavigationUiState
import com.example.travel_buddy.viewmodel.NavigationViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    //private lateinit var travelPointViewModel: DataEntryViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        //travelPointViewModel = ViewModelProvider(this).get(DataEntryViewModel::class.java)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Travel_BuddyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Travel buddy",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

        /*
        travelPointViewModel = ViewModelProvider(this).get(DataEntryViewModel::class.java)

        travelPointViewModel.allTravelPoints.observe(this, { travelPoints ->
            // Update UI with list of travel points
        })

        val newTravelPoint = Travel_point(name = "Santa Claus Village")
        travelPointViewModel.insertTravelPoint(newTravelPoint)

         */
    }
}

@Composable
fun Greeting(name: String, dataEntryViewModel: DataEntryViewModel = viewModel(), navigationViewModel: NavigationViewModel = viewModel(), modifier: Modifier = Modifier) {

    //val travelPoints by dataEntryViewModel.allTravelPoints.observeAsState(initial = emptyList())
    //val travelPointWithTrips by dataEntryViewModel.getTravelPointWithTrips(1).observeAsState()


    val test_travel = Travel_point("travel class")
    val test_attraction = Attraction_point(name="santa claus post office")
    val test_hotel = Hotel_point(name="rovaniemi hotel")
    val test_trip = Trip_point(name="santa claus village")
    val test_trip2 = Trip_point(name="santa claus village2")

    /*

    //This is only for testing, never insert objects into database directly in @Composable or LaunchedEffect, because any recomposition with observeAsState() will lead to infinite insert loop
    //Insert functions should be called only on user actions, for example buttons

    dataEntryViewModel.insertTravelPoint(test_travel)
    dataEntryViewModel.insertTripPoint(test_trip)
    dataEntryViewModel.insertTripPoint(test_trip2)
    dataEntryViewModel.insertHotelPoint(test_hotel)
    dataEntryViewModel.insertAttractionPoint(test_attraction)

     */

    val travelPointManager = Travel_Point_Manager(dataEntryViewModel)

    travelPointManager.add_Point("Vacation", Hotel_point(name = "Hotel1"))
    travelPointManager.add_Point("Vacation", Travel_point(name = "Mountains"))
    //travelPointManager.add_Point("Business", Attraction_point(name = "Restaurant"))

    val list:List<Hotel_point> = listOf(Hotel_point(name="Hotel2"), Hotel_point(name="Hotel3"))

    //travelPointManager.replace_point_List("Vacation",list)
    //travelPointManager.replace_point_from("Vacation",4,Hotel_point(name = "Novotel"))

    travelPointManager.deleteAnyPoint(4,"Vacation")

    //travelPointManager.add_Point("Business", Trip_point(name = "Stockholm"))
    Column {
        Text(
            text = "Hello $name! \n $test_travel \n $test_attraction \n $test_hotel \n $test_trip",
            modifier = modifier
        )
        Text(
            text = "$travelPointManager",
            modifier = modifier
        )
    }

    val test = Travel_point()
    val test2 = Travel_point()
    val test3 = Travel_point()
    val travel_points = arrayOf(test,test2,test3)
    val context = LocalContext.current
    exportToPdf(context,"testpdf",travel_points)

    TestScreen(navigationViewModel.navigationUiState)

    Button(
        colors = ButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContentColor = MaterialTheme.colorScheme.tertiary,
            disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer
        ),
        modifier = Modifier.padding(top = 400.dp, start = 12.dp, end = 12.dp)
            .fillMaxWidth(),
        onClick = {
            navigationViewModel.counter++
            navigationViewModel.getRoutesList(
                    "52.50931,13.42936",
                "52.50274,13.43872",
                    "en-GB",
                    true,
                "car"
                )
        },
        content = { Text("Test Directions API")}
    )

}

@Composable
fun TestScreen(uiState: NavigationUiState) {
    val modifier = Modifier.padding(top=265.dp)
    when (uiState) {
        is NavigationUiState.NoRequest -> Log.d("LOADING","yes")
        is NavigationUiState.Success -> Log.d("Success", uiState.route.toString())
        is NavigationUiState.Error -> Log.d("ERROR","yes")
    }
}