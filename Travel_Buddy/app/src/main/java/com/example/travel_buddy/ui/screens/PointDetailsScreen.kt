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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.travel_buddy.R
import com.example.travel_buddy.classes_res.Travel_Point_Manager
import com.example.travel_buddy.classes_res.Travel_point
import com.example.travel_buddy.classes_res.heritage_points.Attraction_point
import com.example.travel_buddy.classes_res.heritage_points.Hotel_point
import com.example.travel_buddy.classes_res.heritage_points.Trip_point
import com.example.travel_buddy.ui.ui_elements.DateTimeInputSection
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
                tempDataViewModel.updateText("Hotel point")
            }
            is Trip_point -> {
                TravelTripDetailPoint(point)
                tempDataViewModel.updateText("Trip point")
            }
            is Attraction_point -> {
                TravelAttractionDetailPoint(point)
                tempDataViewModel.updateText("Attraction point")
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
            .padding(bottom = 4.dp, start = 8.dp, end = 8.dp),
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
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 10.dp, bottom = 16.dp)
            )
            Card (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
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
                            .align(Alignment.CenterVertically)
                    ){
                        Text(text = point.start_country, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.align(Alignment.CenterHorizontally))
                        if(point.start_subdivision != "No subdivision" && point.start_subdivision != "null")
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
                        Text(text = point.distance, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.align(Alignment.CenterHorizontally))
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
                            .align(Alignment.CenterVertically)
                    ){
                        Text(text = point.dest_country, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.align(Alignment.CenterHorizontally))
                        if(point.dest_subdivision != "No subdivision" && point.dest_subdivision != "null")
                            Text(text = point.dest_subdivision, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.align(Alignment.CenterHorizontally))
                        Text(text = point.dest_name, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.align(Alignment.CenterHorizontally))
                    }
                }
            }
            DateDisplay(point, "Start: ", "End: ", 0, 0)
            if(point.urlToPhoto != "" || point.notes != "") {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, bottom = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Column(
                        modifier = Modifier.padding(6.dp)
                    ) {
                        if (point.urlToPhoto != "")
                            Text(
                                text = point.urlToPhoto,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(2.dp)
                            )
                        if (point.notes != "")
                            Text(
                                text = point.notes,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(2.dp)
                            )
                    }
                }
            }
        }
    }
    /*
        var end_location: Location? = null,
        time: Duration = end_date - date,
        override var location: Location? = null,

        name: String = "No name point",
        var end_location: Location? = null,
        date: Date = Date(),
        end_date: Date = date,
        time: Duration = end_date - date,
        override var location: Location? = null,
        var plan_name: String = "It belongs to no name plan",
        notes: String = "",
        var start_subdivision: String = "No subdivision",
        var start_country: String = "",
        var dest_name: String = "",
        var dest_subdivision: String = "No subdivision",
        var dest_country: String = "",
        var distance: String = "",
        var urlToPhoto: String = ""
    */
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
            .padding(bottom = 4.dp, start = 8.dp, end = 8.dp),
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
                    .padding(horizontal = 8.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Card(
                    modifier = Modifier
                        .width(260.dp)
                        .padding(start = 4.dp, end = 4.dp),
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
                                text = point.date.toString().substringBefore(" ").trim(),
                                style = MaterialTheme.typography.bodyLarge,
                                modifier =  Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp).align(Alignment.CenterHorizontally)
                            )
                            Text(
                                text = point.date.toString().substringAfter(" ").trim(),
                                style = MaterialTheme.typography.bodyLarge,
                                modifier =  Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp).align(Alignment.CenterHorizontally)
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
            DateDisplay(point, "Start: ", "End: ")
            Card (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
            ){
                Column (
                    modifier = Modifier.padding(6.dp)
                ){
                    if(point.city != "")
                        Text(text = point.city , style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(2.dp))
                    if(point.neighborhood != "")
                        Text(text = point.neighborhood , style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(2.dp))
                    if(point.phone != "")
                        Text(text = point.phone , style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(2.dp))
                    if(point.freeFormAddress != "")
                        Text(text = point.freeFormAddress , style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(2.dp))
                    if(point.notes != "")
                        Text(text = point.notes , style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(2.dp))
                }
            }
        }
    }

    /*
        name: String = "No name point",
        date: Date = Date(),
        end_date: Date = date,
        time: Duration = end_date - date,
        location: Location? = null,
        newId: Int = 0, // Nowe pole ID
        travel_plan_name: String = "No travel plan name",
        var city: String = "",
        var neighborhood: String = "",
        var phone: String = "",
        var freeFormAddress: String = "",
        var meetingPoint: String = "",
        notes: String = ""
    * */
}

@Composable
fun TravelHotelDetailPoint(point: Hotel_point) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp, start = 8.dp, end = 8.dp),
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
            Card (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
            ){
                Column (
                    modifier = Modifier.padding(6.dp)
                ){
                    if(point.neighborhood != "")
                        Text(text = point.neighborhood , style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(2.dp))
                    if(point.phone != "")
                        Text(text = point.phone , style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(2.dp))
                    if(point.url != "")
                        Text(text = point.url , style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(2.dp))
                    if(point.freeFormAddress != "")
                        Text(text = point.freeFormAddress , style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(2.dp))
                    if(point.notes != "")
                        Text(text = point.notes , style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(2.dp))
                }
            }
        }
    }

    /*
        time: Duration = end_date - date,
        location: Location? = null,


        name: String = "No name point",
        val city: String = "",
        date: Date = Date(),
        end_date: Date = date,
        time: Duration = end_date - date,
        location: Location? = null,
        val neighborhood: String = "",
        val phone: String = "",
        val url: String = "",
        val freeFormAddress: String = "",
        newId: Int = 0,
        travel_plan_name: String = "No travel plan name",
        notes: String=""
    */
}


@Composable
fun DateDisplay(point: Travel_point, firstText: String, secondText: String, padding:Int = 8, cardPadding:Int = 2) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp, start = padding.dp, end = padding.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        Card(
            modifier = Modifier
                .width(180.dp)
                .padding(start = cardPadding.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Column(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = firstText,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(
                        top = 6.dp,
                        bottom = 6.dp,
                        start = 6.dp,
                        end = 2.dp
                    ).align(Alignment.CenterHorizontally)
                )
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.padding(5.dp)
                ) {
                    Text(
                        text = point.date.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(6.dp)
                    )

                }
            }
        }

        Card(
            modifier = Modifier
                .width(180.dp)
                .padding(start = 2.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Column(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = secondText,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(
                        top = 6.dp,
                        bottom = 6.dp,
                        start = 6.dp,
                        end = 2.dp
                    ).align(Alignment.CenterHorizontally)
                )
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.padding(5.dp)
                ) {
                    Text(
                        text = point.end_date.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(6.dp)
                    )

                }
            }
        }
    }
}