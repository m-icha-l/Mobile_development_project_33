package com.example.travel_buddy.ui.screens

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.sp
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
fun DetailsScreen(viewModel: DataEntryViewModel, navController: NavController, tempDataViewModel: TempDataViewModel, modifier: Modifier = Modifier, travelManager: Travel_Point_Manager,Index: String? = null)
{

    val points = travelManager.display_trip(Index.toString())

    LazyColumn (
        modifier = modifier.padding(start = 8.dp, end = 8.dp)
    ){
        items (points?.points_list!!){ point ->
            when (point) {
                is Hotel_point -> {
                    TravelHotelDetailItem(point)
                    {
                        tempDataViewModel.lastTravel = Index + " -> " + point.name
                        navController.navigate("PointDetailsScreen/$Index/${point.newId}")
                    }
                }
                is Trip_point -> {
                    TravelTripDetailItem(point)
                    {
                        tempDataViewModel.lastTravel = Index + " -> " + point.name
                        navController.navigate("PointDetailsScreen/$Index/${point.newId}")
                    }
                }
                is Attraction_point -> {
                    TravelAttractionDetailItem(point)
                    {
                        tempDataViewModel.lastTravel = Index + " -> " + point.name
                        navController.navigate("PointDetailsScreen/$Index/${point.newId}")
                    }
                }
                else -> {
                    TravelDetailItem(point)
                    {
                        tempDataViewModel.lastTravel = Index + " -> " + point.name
                        navController.navigate("PointDetailsScreen/$Index/${point.newId}")
                    }
                }
            }
        }
    }

    Add_btn(tempDataViewModel,navController, Index)
}

@Composable
fun TravelDetailItem(point: Travel_point, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = point.name, style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 10.dp, bottom = 5.dp))
                Text(text = "Default", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun TravelTripDetailItem(point: Trip_point, onClick: () -> Unit) {


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)
            .clickable { onClick() },

        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {

            Text(
                text = point.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 10.dp, bottom = 10.dp)
            )
            Card (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
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
                            .width(80.dp)
                    ){
                        Text(text = point.start_country, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.align(Alignment.CenterHorizontally))
                        if(point.start_subdivision != "No subdivision")
                            Text(text = point.start_subdivision, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.align(Alignment.CenterHorizontally))
                    }
                    Card (
                        modifier = Modifier.padding(5.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
                    ){
                        Text("A",modifier = Modifier.padding(12.dp), fontSize = 20.sp)
                    }
                    Column (
                        modifier = Modifier
                            .width(80.dp)
                    ){
                        Text(text = point.distance +"km", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.align(Alignment.CenterHorizontally))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Arrow",
                            modifier = Modifier.width(100.dp)
                        )
                    }
                    Card (
                        modifier = Modifier.padding(5.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
                    ){
                        Text("B",modifier = Modifier.padding(12.dp), fontSize = 20.sp)
                    }
                    Column (
                        modifier = Modifier
                            .width(80.dp)
                            .padding(start = 5.dp)
                    ){
                        Text(text = point.dest_country, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.align(Alignment.CenterHorizontally))
                        if(point.dest_subdivision != "No subdivision")
                            Text(text = point.dest_subdivision, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.align(Alignment.CenterHorizontally))
                        Text(text = point.dest_name, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.align(Alignment.CenterHorizontally))
                    }
                }
            }
        }
    }
    /*
        var urlToPhoto: String = ""
    */
}

@Composable
fun TravelAttractionDetailItem(point: Attraction_point, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {

            Text(
                text = point.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 10.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Card(
                    modifier = Modifier
                        .width(260.dp)
                        .padding(end = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Column (
                        modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth()
                    ){
                        Text(
                            text = "Meeting point: ",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(5.dp).align(Alignment.CenterHorizontally)
                        )
                        Card (
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                            modifier = Modifier.padding(5.dp).align(Alignment.CenterHorizontally)
                        ){
                            Text(
                                text = point.meetingPoint,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(6.dp).align(Alignment.CenterHorizontally)
                            )

                        }
                    }
                }

                Card (
                    modifier = Modifier
                        .width(100.dp)
                        .padding(start = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
                ){
                        Card (
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                            modifier = Modifier.padding(8.dp)
                        ){
                            Text(
                                text = point.date.toString(),
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(8.dp)
                            )

                        }
                }
            }
        }
    }
}

@Composable
fun TravelHotelDetailItem(point: Hotel_point, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {

            Text(
                text = point.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 10.dp, bottom = 5.dp)
            )
            Text(
                text = point.city,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(4.dp)
            )
            DateDisplay(point, "Check in: ", "Check out: ")
        }
    }
}