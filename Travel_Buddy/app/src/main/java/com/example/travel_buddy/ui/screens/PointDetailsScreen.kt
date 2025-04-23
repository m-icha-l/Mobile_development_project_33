package com.example.travel_buddy.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.travel_buddy.R
import com.example.travel_buddy.classes_res.Travel_Point_Manager
import com.example.travel_buddy.classes_res.Travel_point
import com.example.travel_buddy.classes_res.heritage_points.Attraction_point
import com.example.travel_buddy.classes_res.heritage_points.Hotel_point
import com.example.travel_buddy.classes_res.heritage_points.Trip_point
import com.example.travel_buddy.viewmodel.DataEntryViewModel
import com.example.travel_buddy.viewmodel.NavigationUiState
import com.example.travel_buddy.viewmodel.NavigationViewModel
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
                DisplayTripPoint(point)
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

        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
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
fun DisplayTripPoint(point: Trip_point) {

    var startText = point.location.toString()

    var destText = point.end_location.toString()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = startText,
                onValueChange = {},
                label = { Text("Starting point") },
                placeholder = { Text(point.location.toString()) },
                modifier = Modifier
                    .weight(1f),
                readOnly = true,
                singleLine = true,
                leadingIcon = {
                    Icon(Icons.Default.LocationOn, contentDescription = "StartLocation")
                }
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = destText,
                onValueChange = { },
                label = { Text("Travel destination") },
                placeholder = { Text(point.end_location.toString()) },
                modifier = Modifier.weight(1f),
                readOnly = true,
                singleLine = true,
                leadingIcon = {
                    Icon(Icons.Default.LocationOn, contentDescription = "StartLocation")
                }
            )
        }

//        DateTimePickerModal(tempDataViewModel)
        OutlinedTextField(
            value = point.date.toString(),
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(point.date.toString()) },
            readOnly = true
        )

        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "Popular travel destinations for:",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text("Finland", style = MaterialTheme.typography.bodyLarge)
                }
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(Color.LightGray)
                ) {
                    // Placeholder for image
                }
            }
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
            containerColor = MaterialTheme.colorScheme.primary
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
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Column {
                        Text(
                            text = "Meeting point: ",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(4.dp)
                        )
                        Card (
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
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
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
                ){
                    Row (
                        modifier = Modifier.padding(4.dp)
                    ){
                        Card (
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
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
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
        ) {

            Text(
                text = point.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 10.dp, bottom = 5.dp)
            )
            Text(
                text = point.location.toString(),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(4.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Card(
                    modifier = Modifier
                        .width(190.dp)
                        .padding(end = 2.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Row {
                        Text(text = "Check in: ", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(6.dp))
                        Card (
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                            modifier = Modifier.padding(2.dp).fillMaxWidth()
                        ){
                            Text(text = point.date.toString(), style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(2.dp))

                        }
                    }

                    Text(text = "Hotel", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(6.dp))
                }

                Card (
                    modifier = Modifier
                        .width(190.dp)
                        .padding(start = 2.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
                ){
                    Row {
                        Text(text = "Check out: ", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(6.dp))
                        Card (
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                            modifier = Modifier.padding(2.dp).fillMaxWidth()
                        ){
                            Text(text = point.end_date.toString(), style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(2.dp))

                        }
                    }
                    Text(text = "Hotel", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(6.dp))
                }
            }
            Text(text = point.city , style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(2.dp))
            Text(text = point.time.toString() , style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(2.dp))
            Text(text = point.notes , style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(2.dp))
        }
    }
}