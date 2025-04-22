package com.example.travel_buddy.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.travel_buddy.R
import com.example.travel_buddy.classes_res.Travel_Point_Manager
import com.example.travel_buddy.classes_res.Travel_point
import com.example.travel_buddy.classes_res.heritage_points.Attraction_point
import com.example.travel_buddy.classes_res.heritage_points.Hotel_point
import com.example.travel_buddy.classes_res.heritage_points.Trip_point
import com.example.travel_buddy.ui.ui_elements.Add_btn
import com.example.travel_buddy.viewmodel.DataEntryViewModel
import com.example.travel_buddy.viewmodel.TempDataViewModel

@Composable
fun DetailsScreen(viewModel: DataEntryViewModel, navController: NavController, tempDataViewModel: TempDataViewModel, modifier: Modifier = Modifier, Index: String? = null)
{

    var travelManager = Travel_Point_Manager(viewModel)
    val points = travelManager.display_trip(Index.toString())

    LazyColumn (
        modifier = modifier
    ){
        items (points?.points_list!!){ point ->
            when (point) {
                is Hotel_point -> {
                    TravelHotelDetailItem(point)
                }
                is Trip_point -> {
                    TravelTripDetailItem(point)
                }
                is Attraction_point -> {
                    TravelAttractionDetailItem(point)
                }
                else -> {
                    TravelDetailItem(point)
                }
            }
        }
    }

    Add_btn(tempDataViewModel,navController)
}

@Composable
fun TravelDetailItem(point: Travel_point) {
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
                Text(text = point.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = "Default", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun TravelTripDetailItem(point: Trip_point) {
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

        }
    }
}

@Composable
fun TravelAttractionDetailItem(point: Attraction_point) {
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
                        Text(text = "Meeting point: ", style = MaterialTheme.typography.bodyLarge)
                        Card (
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy())
                        ){
                            Text(text = point.location.toString(), style = MaterialTheme.typography.bodyLarge)

                        }
                    }
                }

                Card (
                    modifier = Modifier
                        .width(80.dp)
                        .padding(4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f))
                ){
                    Row {
                        Card (
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy())
                        ){
                            Text(text = point.end_date.toString(), style = MaterialTheme.typography.bodyLarge)

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TravelHotelDetailItem(point: Hotel_point) { //change from trip point data to hotel
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
            Text(
                text = point.location.toString(),
                style = MaterialTheme.typography.bodyMedium,
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
                        .width(180.dp)
                        .padding(4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f))
                ) {
                    Row {
                        Text(text = "Check in: ", style = MaterialTheme.typography.bodyLarge)
                        Card (
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy())
                        ){
                            Text(text = point.date.toString(), style = MaterialTheme.typography.bodyLarge)

                        }
                    }

                    Text(text = "Hotel", style = MaterialTheme.typography.bodyMedium)
                }

                Card (
                    modifier = Modifier
                        .width(180.dp)
                        .padding(4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f))
                ){
                    Row {
                        Text(text = "Check out: ", style = MaterialTheme.typography.bodyLarge)
                        Card (
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy())
                        ){
                            Text(text = point.end_date.toString(), style = MaterialTheme.typography.bodyLarge)

                        }
                    }
                    Text(text = "Hotel", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}