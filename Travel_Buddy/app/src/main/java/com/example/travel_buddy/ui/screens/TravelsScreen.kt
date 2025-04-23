package com.example.travel_buddy.ui.screens

import android.location.Location
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.travel_buddy.classes_res.Date
import com.example.travel_buddy.classes_res.Duration
import com.example.travel_buddy.classes_res.Travel_Point_Manager
import com.example.travel_buddy.classes_res.Travel_point
import com.example.travel_buddy.classes_res.Trip
import com.example.travel_buddy.classes_res.heritage_points.Attraction_point
import com.example.travel_buddy.classes_res.heritage_points.Hotel_point
import com.example.travel_buddy.classes_res.heritage_points.Trip_point
import com.example.travel_buddy.viewmodel.DataEntryViewModel
import com.example.travel_buddy.viewmodel.TempDataViewModel

@Composable
fun TravelsScreen(viewModel: DataEntryViewModel, navController: NavController, tempDataViewModel: TempDataViewModel, modifier: Modifier)
{
    var index = 1
    DataEntryViewModel.TopBarName.updateText(index.toString())

    var travelManager = Travel_Point_Manager(viewModel)

    //Temporary for testing purposes
    val name = "New point"
    val date = Date("01/05/2025 10:00")
    val end_date = Date("06/05/2025 15:21")
    var notes: String = ""
    var location: Location? = null
    var newId = 0
    var travelPlanName = "New travel plan"
//    val newPoint = Travel_point(name, date, end_date, null ,location,"",newId, travelPlanName)


    var time: Duration = end_date - date
    val city: String = "Oulu"
    val newHotel = Hotel_point(name, city, date, end_date, time,  location, newId, travelPlanName, notes)


    val newAttra = Attraction_point(name, date, end_date, time, location, newId, travelPlanName )

    val newTrip = Trip_point(newId, name, location,date, end_date, time, location, travelPlanName)

    val names = travelManager.display_all_trips()



    Column(
        modifier = modifier
    ){

        if(names != null)
        {

            LazyColumn{
                items (names){ name ->
                    TravelPlanItem(name)
                    {
                        tempDataViewModel.lastTravel = name.trip_name
                        navController.navigate("DetailsScreen/${name.trip_name}")
                    }
                }
            }
        }
/*        else
        {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
            )
            {
//                travelManager.add_Point(travelPlanName, newPoint)
                travelManager.add_Point(travelPlanName, newHotel)
                travelManager.add_Point(travelPlanName, newAttra)
                travelManager.add_Point(travelPlanName, newTrip)
            }
        }*/
    }

}

@Composable
fun TravelPlanItem(trip : Trip, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                val trip_duration = trip.points_list[trip.points_list.lastIndex].end_date - trip.points_list[0].date
                Text(text = trip.trip_name, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(4.dp))
                Text(text = "Length: $trip_duration", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(4.dp))
            }
        }
    }

}