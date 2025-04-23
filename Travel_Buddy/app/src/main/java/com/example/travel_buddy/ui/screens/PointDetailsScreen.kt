package com.example.travel_buddy.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.travel_buddy.classes_res.Travel_Point_Manager
import com.example.travel_buddy.classes_res.Travel_point
import com.example.travel_buddy.classes_res.heritage_points.Attraction_point
import com.example.travel_buddy.classes_res.heritage_points.Hotel_point
import com.example.travel_buddy.classes_res.heritage_points.Trip_point
import com.example.travel_buddy.viewmodel.DataEntryViewModel
import com.example.travel_buddy.viewmodel.TempDataViewModel

@Composable
fun PointDetailsScreen(viewModel: DataEntryViewModel, navController: NavController, tempDataViewModel: TempDataViewModel, modifier: Modifier = Modifier,  trip_name: String?,  pointIndex: Int? = null)
{
    var travelManager = Travel_Point_Manager(viewModel)
    val point = travelManager.display_trip_point(trip_name.toString(), pointIndex)
    Column(
        modifier = modifier
    ){
        when (point) {
            is Hotel_point -> {
                TravelHotelDetailPoint(point)
            }
            is Trip_point -> {
                TravelTripDetailPoint(point)
            }
            is Attraction_point -> {
                TravelAttractionDetailPoint(point)
            }
            else -> {
                TravelDetailPoint(point)
            }
        }
    }
}

@Composable
fun TravelDetailPoint(point: Travel_point?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = point?.name ?: "Default Name", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Default", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun TravelTripDetailPoint(point: Trip_point) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp),

        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {

            Text(
                text = point.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Card (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f))
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    Column (
                        modifier = Modifier
                            .width(100.dp)
                    ){
                        Text(text = point.location.toString(), style = MaterialTheme.typography.bodyLarge)
                    }
                    Column (
                        modifier = Modifier
                            .width(100.dp)
                    ){
                        Text(text = point.time.toString(), style = MaterialTheme.typography.bodyLarge, modifier = Modifier.align(Alignment.CenterHorizontally))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Arrow",
                            modifier = Modifier.width(100.dp)
                        )
                    }
                    Column (
                        modifier = Modifier
                            .width(100.dp)
                    ){
                        Text(text = point.end_location.toString(), style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
            Text(text = point.date.toString() , style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(2.dp))
            Text(text = point.end_date.toString() , style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(2.dp))
            Text(text = point.notes , style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(2.dp))
            /*
                date: Date = Date(),
                end_date: Date = date,
                notes: String = ""
             */
        }
    }
}

@Composable
fun TravelAttractionDetailPoint(point: Attraction_point) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(
                alpha = 0.3f
            )
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {

            Text(
                text = point.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Card(
                    modifier = Modifier
                        .width(230.dp)
                        .padding(4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f))
                ) {
                    Column {
                        Text(
                            text = "Meeting point: ",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(4.dp)
                        )
                        Card (
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy())
                        ){
                            Text(
                                text = point.location.toString(),
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(4.dp)
                            )

                        }
                    }
                }

                Card (
                    modifier = Modifier
                        .width(90.dp)
                        .padding(4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f))
                ){
                    Row (
                        modifier = Modifier.padding(4.dp)
                    ){
                        Card (
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy())
                        ){
                            Text(
                                text = point.date.toString(),
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(4.dp)
                            )

                        }
                    }
                }
            }
            Text(text = point.end_date.toString() , style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(2.dp))
            Text(text = point.time.toString() , style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(2.dp))
            Text(text = point.notes , style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(2.dp))
            /*
                end_date: Date = date,
                time: Duration = end_date - date,
                notes: String = ""
             */
        }
    }
}

@Composable
fun TravelHotelDetailPoint(point: Hotel_point) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(
                alpha = 0.3f
            )
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
        ) {

            Text(
                text = point.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(4.dp)
            )
            Text(
                text = point.location.toString(),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(4.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Card(
                    modifier = Modifier
                        .width(180.dp)
                        .padding(2.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f))
                ) {
                    Row {
                        Text(text = "Check in: ", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(2.dp))
                        Card (
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy()),
                            modifier = Modifier.padding(2.dp).width(100.dp)
                        ){
                            Text(text = point.date.toString(), style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(2.dp))

                        }
                    }
                }

                Card (
                    modifier = Modifier
                        .width(200.dp)
                        .padding(4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f))
                ){
                    Row {
                        Text(text = "Check out: ", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(2.dp))
                        Card (
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy()),
                            modifier = Modifier.padding(2.dp).width(140.dp)
                        ){
                            Text(text = point.end_date.toString(), style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(2.dp))
                        }
                    }
                }
            }
            Text(text = point.city , style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(2.dp))
            Text(text = point.time.toString() , style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(2.dp))
            Text(text = point.notes , style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(2.dp))
        }
    }
}