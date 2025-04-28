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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.travel_buddy.classes_res.Date
import com.example.travel_buddy.classes_res.Duration
import com.example.travel_buddy.classes_res.Travel_Point_Manager
import com.example.travel_buddy.classes_res.Travel_point
import com.example.travel_buddy.classes_res.Trip
import com.example.travel_buddy.classes_res.heritage_points.Attraction_point
import com.example.travel_buddy.classes_res.heritage_points.Hotel_point
import com.example.travel_buddy.classes_res.heritage_points.Trip_point
import com.example.travel_buddy.ui.ui_elements.Add_btn
import com.example.travel_buddy.viewmodel.DataEntryViewModel
import com.example.travel_buddy.viewmodel.TempDataViewModel

@Composable
fun TravelsScreen(viewModel: DataEntryViewModel, navController: NavController, tempDataViewModel: TempDataViewModel, modifier: Modifier, travelManager: Travel_Point_Manager)
{
    var index = 1
    DataEntryViewModel.TopBarName.updateText(index.toString())

/*    //var travelManager = Travel_Point_Manager(viewModel)

    //Temporary for testing purposes
    var name = "Hotel Point"
    var date = Date("01/05/2025 10:00")
    var end_date = Date("06/05/2025 17:00")
    var notes: String = "Hotel *****"
    var location: Location? = null
    var newId = 0
    var travelPlanName = "Demo travel plan"
    var time: Duration = end_date - date
    val city: String = "Oulu"
    val newHotel = Hotel_point(name, city, date, end_date, time,  location, newId, travelPlanName, notes)

    name = "Attraction Point"
    date = Date("02/05/2025 14:00")
    end_date = Date("02/05/2025 16:00")
    notes = "Cathedral"
    val newAttra = Attraction_point(name, date, end_date, time, location, newId, travelPlanName )

    name = "Trip Point"
    date = Date("01/05/2025 5:00")
    end_date = Date("01/05/2025 10:00")
    notes = "Stop in Jyvaskyla"
    val newTrip = Trip_point(newId, name, location,date, end_date, time, location, travelPlanName)*/
    val names = travelManager.display_all_trips()



    Column(
        modifier = modifier.padding(start = 8.dp, end = 8.dp)
    ){
        Column (
            modifier = Modifier.fillMaxWidth()
        ){
            Text(
                text = "Welcome",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onTertiary,
                fontSize = 80.sp,
                modifier = Modifier.padding(bottom = 25.dp).align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Where are we going?",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.5f),
                modifier = Modifier.padding(bottom = 25.dp).align(Alignment.CenterHorizontally)
            )
        }

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
    }

    Add_btn(tempDataViewModel,navController,"", travelManager,"travel plan")

}

@Composable
fun TravelPlanItem(trip : Trip, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                val trip_duration = trip.points_list[trip.points_list.lastIndex].end_date - trip.points_list[0].date
                val days = trip_duration.toString().substringBefore(",").trim()
                val hours = trip_duration.toString().substringAfter(",").trim()
                var daysInt = days.toInt()
                if(hours != "0:0")
                    daysInt++

                Text(text = trip.trip_name, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(4.dp))
                Text(text = "$daysInt days", style = MaterialTheme.typography.titleMedium   , modifier = Modifier.padding(4.dp))
            }
        }
    }

}