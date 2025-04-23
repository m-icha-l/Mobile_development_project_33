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
        modifier = modifier
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
fun TravelTripDetailItem(point: Trip_point, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)
            .clickable { onClick() },

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
fun TravelAttractionDetailItem(point: Attraction_point, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)
            .clickable { onClick() },
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

                    Text(text = "Hotel", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(4.dp))
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
                    Text(text = "Hotel", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(4.dp))
                }
            }
        }
    }
}