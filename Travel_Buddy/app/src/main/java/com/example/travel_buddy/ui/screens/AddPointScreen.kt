package com.example.travel_buddy.ui.screens

import androidx.compose.material3.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.icons.outlined.LocationCity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.travel_buddy.viewmodel.DataEntryViewModel
import com.example.travel_buddy.viewmodel.TempDataViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travel_buddy.R
import com.example.travel_buddy.classes_res.Travel_Point_Manager
import com.example.travel_buddy.classes_res.heritage_points.Trip_point
import com.example.travel_buddy.classes_res.model.RouteResponse
import com.example.travel_buddy.viewmodel.NavigationUiState
import com.example.travel_buddy.viewmodel.NavigationViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import android.location.Location
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.travel_buddy.classes_res.Date
import com.example.travel_buddy.classes_res.heritage_points.Attraction_point
import com.example.travel_buddy.classes_res.heritage_points.Hotel_point
import com.example.travel_buddy.functions.parseLocation
import com.example.travel_buddy.ui.ui_elements.AttractionCards
import com.example.travel_buddy.ui.ui_elements.AttractionInput
import com.example.travel_buddy.ui.ui_elements.AttractionSuggestionsSection
import com.example.travel_buddy.ui.ui_elements.DateTimeInputSection
import com.example.travel_buddy.ui.ui_elements.HotelCards
import com.example.travel_buddy.ui.ui_elements.HotelInputSection
import com.example.travel_buddy.ui.ui_elements.HotelSuggestionsSection
import com.example.travel_buddy.viewmodel.PlacesUiState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddPointScreen(viewModel: DataEntryViewModel, navController: NavController, tempDataViewModel: TempDataViewModel, modifier: Modifier, travelManager: Travel_Point_Manager,type: String?,tripName: String?, editPointIndex: Int? = -1)
{
    when (type) {
        "Trip Point" -> AddTripPoint(navController,tempDataViewModel,modifier,travelManager, tripName, editPointIndex)
        "Hotel Point" -> AddHotelPoint(navController,tempDataViewModel,modifier,travelManager, tripName, editPointIndex)
        "Attraction Point" -> AddAttractionPoint(navController,tempDataViewModel,modifier,travelManager, tripName, editPointIndex)
        else -> Text (
            modifier = modifier,
            text = "Error"
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun EditTripPoint(travelManager: Travel_Point_Manager, tempDataViewModel: TempDataViewModel, start_date: LocalDateTime, end_date: LocalDateTime, start_loc: Location, end_loc: Location, tripName: String, index: Int,output_format:DateTimeFormatter) {
        travelManager.replace_point_from(tripName,index,Trip_point(
            name = tempDataViewModel.start_city_name,
            location = start_loc,
            end_location = end_loc,
            date = Date(start_date.format(output_format)),
            end_date = Date(end_date.format(output_format)),
            plan_name = tripName,
            start_subdivision = tempDataViewModel.start_subdivision,
            start_country = tempDataViewModel.start_country,
            dest_name = tempDataViewModel.dest_city_name,
            dest_subdivision = tempDataViewModel.dest_subdivision,
            dest_country = tempDataViewModel.dest_country,
            distance = tempDataViewModel.distance,
            notes = tempDataViewModel.tripNote
        ))
}

@RequiresApi(Build.VERSION_CODES.O)
fun EditHotelPoint(travelManager: Travel_Point_Manager, tempDataViewModel: TempDataViewModel, start_date: LocalDateTime, end_date: LocalDateTime, start_loc: Location, tripName: String, index: Int, output_format:DateTimeFormatter) {
    travelManager.replace_point_from(
        tripName, index, Hotel_point(
            name = tempDataViewModel.selectedHotel,
            location = start_loc,
            date = Date(start_date.format(output_format)),
            end_date = Date(end_date.format(output_format)),
            city = tempDataViewModel.hotelMunicipality,
            neighborhood = tempDataViewModel.hotelNeighborhood,
            phone = tempDataViewModel.hotelPhone,
            url = tempDataViewModel.hotelUrl,
            freeFormAddress = tempDataViewModel.hotelFreeFormAddress,
            travel_plan_name = tripName,
            notes = tempDataViewModel.hotelNote
        )
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun EditAttractionPoint(travelManager: Travel_Point_Manager, tempDataViewModel: TempDataViewModel, start_date: LocalDateTime, start_loc: Location, tripName: String, index: Int, output_format:DateTimeFormatter) {
    travelManager.replace_point_from(
        tripName, index, Attraction_point(
            name = tempDataViewModel.selectedAttraction,
            location = start_loc,
            date = Date(start_date.format(output_format)),
            city = tempDataViewModel.attractionMunicipality,
            neighborhood = tempDataViewModel.attrNeighborhood,
            phone = tempDataViewModel.attrPhone,
            freeFormAddress = tempDataViewModel.attrFreeFormAddress,
            meetingPoint = tempDataViewModel.meetingPoint,
            travel_plan_name = tripName,
            notes = tempDataViewModel.attractionNote
        )
    )
}

fun getEditedTripPointData(travelManager: Travel_Point_Manager, tempDataViewModel: TempDataViewModel,tripName: String?, editPointIndex: Int?) { //Data is stored in tempDataViewModel
    if(tripName != null && editPointIndex != null) {
        val editedTripPoint = travelManager.display_trip_point(tripName,editPointIndex)
        val st = editedTripPoint?.date
        val e = editedTripPoint?.end_date
        when(editedTripPoint) {
            is Trip_point -> {
                tempDataViewModel.start_isSet = true
                tempDataViewModel.dest_isSet = true
                tempDataViewModel.start_city_name = editedTripPoint.name
                tempDataViewModel.start_latitude = editedTripPoint.location?.latitude!!
                tempDataViewModel.start_longtitude = editedTripPoint.location?.longitude!!
                tempDataViewModel.dest_latitude = editedTripPoint.end_location?.latitude!!
                tempDataViewModel.dest_longtitude = editedTripPoint.end_location?.longitude!!
                if (st != null) {
                    tempDataViewModel.selectedDateTime = LocalDateTime.of(st.year, st.month, st.day, st.hour, st.minute)
                }
                if (e != null) {
                    tempDataViewModel.endDateTime = LocalDateTime.of(e.year, e.month, e.day, e.hour, e.minute)
                }
                tempDataViewModel.start_subdivision = editedTripPoint.start_subdivision
                tempDataViewModel.start_country =  editedTripPoint.start_country
                tempDataViewModel.dest_city_name = editedTripPoint.dest_name
                tempDataViewModel.dest_subdivision = editedTripPoint.dest_subdivision
                tempDataViewModel.dest_country = editedTripPoint.dest_country
                tempDataViewModel.distance = editedTripPoint.distance
                tempDataViewModel.tripNote = editedTripPoint.notes
            }
            is Hotel_point -> {
                tempDataViewModel.selectedHotel = editedTripPoint.name
                tempDataViewModel.hotelLatitude = editedTripPoint.location?.latitude ?: 0.0
                tempDataViewModel.hotelLongitude = editedTripPoint.location?.longitude ?: 0.0
                if (st != null) {
                    tempDataViewModel.checkInDateTime = LocalDateTime.of(st.year, st.month, st.day, st.hour, st.minute)
                }
                if (e != null) {
                    tempDataViewModel.checkOutDateTime = LocalDateTime.of(e.year, e.month, e.day, e.hour, e.minute)
                }
                tempDataViewModel.hotelMunicipality = editedTripPoint.city
                tempDataViewModel.hotelNeighborhood = editedTripPoint.neighborhood
                tempDataViewModel.hotelPhone = editedTripPoint.phone
                tempDataViewModel.hotelUrl = editedTripPoint.url
                tempDataViewModel.hotelFreeFormAddress = editedTripPoint.freeFormAddress
                tempDataViewModel.hotelNote = editedTripPoint.notes
            }
            is Attraction_point -> {
                tempDataViewModel.selectedAttraction = editedTripPoint.name
                tempDataViewModel.attractionLatitude = editedTripPoint.location?.latitude ?: 0.0
                tempDataViewModel.attractionLongitude = editedTripPoint.location?.longitude ?: 0.0
                if (st != null) {
                    tempDataViewModel.selectedAttractionDateTime = LocalDateTime.of(st.year, st.month, st.day, st.hour, st.minute)
                }
                tempDataViewModel.attractionMunicipality = editedTripPoint.city
                tempDataViewModel.attrNeighborhood = editedTripPoint.neighborhood
                tempDataViewModel.attrPhone = editedTripPoint.phone
                tempDataViewModel.attrFreeFormAddress = editedTripPoint.freeFormAddress
                tempDataViewModel.meetingPoint = editedTripPoint.meetingPoint
                tempDataViewModel.attractionNote = editedTripPoint.notes
            }
        }
    }
}

fun clearTripPointData(tempDataViewModel: TempDataViewModel) {
    tempDataViewModel.startingPoint = ""
    tempDataViewModel.destinationPoint = ""
    tempDataViewModel.start_isSet = false
    tempDataViewModel.dest_isSet = false
    tempDataViewModel.start_city_name = ""
    tempDataViewModel.start_latitude = 0.0
    tempDataViewModel.start_longtitude = 0.0
    tempDataViewModel.dest_latitude = 0.0
    tempDataViewModel.dest_longtitude = 0.0
    tempDataViewModel.selectedDateTime = null
    tempDataViewModel.endDateTime = null
    tempDataViewModel.start_subdivision = ""
    tempDataViewModel.start_country =  ""
    tempDataViewModel.dest_city_name = ""
    tempDataViewModel.dest_subdivision = ""
    tempDataViewModel.dest_country = ""
    tempDataViewModel.distance = ""
    tempDataViewModel.tripNote = ""
    tempDataViewModel.redirectFromBrowser = false
}

fun clearHotelPointData(tempDataViewModel: TempDataViewModel) {
    tempDataViewModel.selectedHotel = ""
    tempDataViewModel.hotelLatitude = 0.0
    tempDataViewModel.hotelLongitude = 0.0
    tempDataViewModel.checkInDateTime = null
    tempDataViewModel.checkOutDateTime = null
    tempDataViewModel.hotelMunicipality = ""
    tempDataViewModel.hotelNeighborhood = ""
    tempDataViewModel.hotelPhone = ""
    tempDataViewModel.hotelUrl = ""
    tempDataViewModel.hotelFreeFormAddress = ""
    tempDataViewModel.hotelNote = ""
    tempDataViewModel.redirectFromBrowser = false
    tempDataViewModel.hotelNoteExpanded = false
}

fun clearAttractionPointData(tempDataViewModel: TempDataViewModel) {
    tempDataViewModel.selectedAttraction = ""
    tempDataViewModel.attractionLatitude = 0.0
    tempDataViewModel.attractionLongitude = 0.0
    tempDataViewModel.selectedAttractionDateTime = null
    tempDataViewModel.attractionMunicipality = ""
    tempDataViewModel.attrNeighborhood = ""
    tempDataViewModel.attrPhone = ""
    tempDataViewModel.attrFreeFormAddress = ""
    tempDataViewModel.meetingPoint = ""
    tempDataViewModel.attractionNote = ""
    tempDataViewModel.redirectFromBrowser = false
    tempDataViewModel.attractionNoteExpanded = false
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePickerModal(tempDataViewModel: TempDataViewModel) {
    val context = LocalContext.current

    val displayFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm")

    // Date picker state
    val datePickerState = rememberDatePickerState()

    // Handle TimePickerDialog after date selection
    val calendar = Calendar.getInstance()

    val timePicker = remember {
        { date: LocalDate ->
            TimePickerDialog(
                context,
                { _, selectedHour, selectedMinute ->
                    tempDataViewModel.selectedDateTime = date.atTime(selectedHour, selectedMinute)
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true // 24-hour format
            ).show()
        }
        }

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            timePicker(LocalDate.of(year,month,dayOfMonth))
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text("Select date", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(8.dp))
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = tempDataViewModel.selectedDateTime?.format(displayFormatter) ?: "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("mm/dd/yyyy hh:mm") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { datePickerDialog.show() }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Pick date and time")
                }
            }
        )
    }
}

@Composable
fun TripInfoCard(routeResponse: RouteResponse, tempDataViewModel: TempDataViewModel) {
    var routeLengthInt = routeResponse.routes[0].summary.lengthInMeters
    var routeLength = routeLengthInt.toString()
    if( routeLengthInt >= 1000) {
        val prefixLength = (routeLengthInt / 1000).toString().length
        if(routeLengthInt % 1000 == 0) {
            routeLength = routeLength.substring(0,prefixLength) + " km"
        } else {
            routeLength = routeLength.substring(0,prefixLength) + "." + routeLength[prefixLength] + " km"
        }
    }
    else {
        routeLength = routeLength + " m"
    }
    val departureTime = tempDataViewModel.selectedDateTime.toString().substring(5,10) + " " + tempDataViewModel.selectedDateTime.toString().substring(11,16)
    val arrivalTime = routeResponse.routes[0].summary.arrivalTime.substring(5,10) + " " + routeResponse.routes[0].summary.arrivalTime.substring(11,16)
    tempDataViewModel.arrivalDate = routeResponse.routes[0].summary.arrivalTime.substring(0,10) + " " + routeResponse.routes[0].summary.arrivalTime.substring(11,16)
    tempDataViewModel.distance = routeLength
    val context = LocalContext.current
    val url = "https://www.google.pl/maps/dir/${tempDataViewModel.start_latitude},${tempDataViewModel.start_longtitude}/${tempDataViewModel.dest_latitude},${tempDataViewModel.dest_longtitude}"
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Distance with Flag Icon
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_flag),
                        contentDescription = "Distance",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = routeLength,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_clock),
                        contentDescription = "Time",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                    //Spacer(modifier = Modifier.width(8.dp))
                    Column(
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text(
                            text = "Departure: $departureTime",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Arrival: $arrivalTime",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }
            }
            Text(
                text = "Click to see the route in Google maps",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddTripPoint(navController: NavController, tempDataViewModel: TempDataViewModel, modifier: Modifier, travelManager: Travel_Point_Manager, tripName: String?,  editPointIndex: Int? = -1, navigationViewModel: NavigationViewModel = viewModel()) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState()

    var fromBrowser: Boolean by remember { mutableStateOf(false)}

    val cameFromBrowser = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.get<Boolean>("cameFromBrowser") == true

    LaunchedEffect(currentBackStackEntry.value) {

        if(!cameFromBrowser && !fromBrowser) {
            if (editPointIndex != -1 && tripName != null && editPointIndex != null) {
                getEditedTripPointData(travelManager, tempDataViewModel, tripName, editPointIndex)
            }
        } else {
            fromBrowser = false
        }
    }

    //navController.currentBackStackEntry
        //?.savedStateHandle
        //?.remove<Boolean>("cameFromBrowser")
    var noteExpanded: Boolean by remember { mutableStateOf(false)}
    if(tempDataViewModel.start_isSet) {
        tempDataViewModel.startingPoint = tempDataViewModel.start_city_name + ", " + tempDataViewModel.start_subdivision + ", " + tempDataViewModel.start_country
    }

    if(tempDataViewModel.dest_isSet) {
        tempDataViewModel.destinationPoint = tempDataViewModel.dest_city_name + ", " + tempDataViewModel.dest_subdivision + ", " + tempDataViewModel.dest_country
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 130.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = tempDataViewModel.startingPoint,
                onValueChange = { tempDataViewModel.startingPoint = it },
                label = { Text("Starting point") },
                placeholder = { Text("Click \"+\" add point") },
                modifier = Modifier
                    .weight(1f),
                readOnly = true,
                singleLine = true,
                leadingIcon = {
                    Icon(Icons.Default.LocationOn, contentDescription = "StartLocation")
                },
                trailingIcon = {
                    if (tempDataViewModel.startingPoint.isNotEmpty()) {
                        IconButton(onClick = { tempDataViewModel.startingPoint = "" }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear")
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.width(8.dp)) // Add space between the TextField and the button

            Surface(
                color = if (isSystemInDarkTheme()) Color(0xFF1E1D6D) else Color(0xFF6562DF),
                shape = CircleShape,
                modifier = Modifier
                    .size(54.dp)
                    .clickable(onClick = {
                        navController.currentBackStackEntry?.savedStateHandle?.set("cameFromBrowser", true)
                        fromBrowser = true
                        tempDataViewModel.browserType = "Select starting point"
                        navController.navigate("Browser/trip_start_point")
                    })
            ) {
                Image(
                    painter = painterResource(id = R.drawable.plus_icon),
                    contentDescription = "Add",
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer(
                            scaleX = 0.5f,
                            scaleY = 0.5f
                        )
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = tempDataViewModel.destinationPoint,
                onValueChange = { tempDataViewModel.destinationPoint = it },
                label = { Text("Travel destination") },
                placeholder = { Text("Click \"+\" add point") },
                supportingText = { Text("Use current location or add point")},
                modifier = Modifier.weight(1f),
                readOnly = true,
                singleLine = true,
                leadingIcon = {
                    Icon(Icons.Default.LocationOn, contentDescription = "StartLocation")
                },
                trailingIcon = {
                    if (tempDataViewModel.destinationPoint.isNotEmpty()) {
                        IconButton(onClick = { tempDataViewModel.destinationPoint = "" }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear")
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.width(8.dp)) // Add space between the TextField and the button

            Surface(
                color = if (isSystemInDarkTheme()) Color(0xFF1E1D6D) else Color(0xFF6562DF),
                shape = CircleShape,
                modifier = Modifier
                    .size(54.dp)
                    .clickable(onClick = {
                        navController.currentBackStackEntry?.savedStateHandle?.set("cameFromBrowser", true)
                        fromBrowser = true
                        tempDataViewModel.browserType = "Select travel destination"
                        navController.navigate("Browser/trip_travel_destination")
                    })
            ) {
                Image(
                    painter = painterResource(id = R.drawable.plus_icon),
                    contentDescription = "Add",
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer(
                            scaleX = 0.5f,
                            scaleY = 0.5f
                        )
                )
            }
        }

        DateTimePickerModal(tempDataViewModel)

        Button(
            onClick = { noteExpanded = !noteExpanded },
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .padding(start = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            border = BorderStroke(1.dp, Color.LightGray)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AddBox,
                    contentDescription = "Location",
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Add note",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        AnimatedVisibility(
            visible = noteExpanded,
            enter = fadeIn(animationSpec = tween(500)) + expandVertically(animationSpec = tween(500)),
            exit = fadeOut(animationSpec = tween(300)) + shrinkVertically(animationSpec = tween(300))
        ) {
            TextField(
                value = tempDataViewModel.tripNote,
                onValueChange = { tempDataViewModel.tripNote = it },
                supportingText = {Text("Write your note...")},
                maxLines = 2,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 16.dp)
            )
        }
        if(tempDataViewModel.start_isSet && tempDataViewModel.dest_isSet && tempDataViewModel.selectedDateTime != null) {
            val startLoc: String = tempDataViewModel.start_latitude.toString() + "," + tempDataViewModel.start_longtitude.toString()
            val endLoc: String = tempDataViewModel.dest_latitude.toString() + "," + tempDataViewModel.dest_longtitude.toString()
            navigationViewModel.getRoutesList(startLoc,endLoc,"en-GB",true,"car",tempDataViewModel.selectedDateTime.toString() + ":00Z")
            tripInfoUiState(navigationViewModel.navigationUiState,tempDataViewModel)
            Button(
                modifier = Modifier.padding(top=16.dp),
                onClick = {
                    val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                    val outputFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                    val startDate = LocalDateTime.parse(tempDataViewModel.selectedDateTime.toString().replace('T', ' '), inputFormat)
                    val endDate = LocalDateTime.parse(tempDataViewModel.arrivalDate, inputFormat)
                        var startLoc = Location("start").apply {
                            latitude = tempDataViewModel.start_latitude
                            longitude =  tempDataViewModel.start_longtitude }
                        var endLoc = Location("end").apply {
                            latitude = tempDataViewModel.dest_latitude
                            longitude = tempDataViewModel.dest_longtitude }
                    if (tripName != null) {
                        if (editPointIndex == -1) {
                            travelManager.add_Point(
                                tripName, Trip_point(
                                    name = tempDataViewModel.start_city_name,
                                    location = startLoc,
                                    end_location = endLoc,
                                    date = Date(startDate.format(outputFormat)),
                                    end_date = Date(endDate.format(outputFormat)),
                                    plan_name = tripName,
                                    start_subdivision = tempDataViewModel.start_subdivision,
                                    start_country = tempDataViewModel.start_country,
                                    dest_name = tempDataViewModel.dest_city_name,
                                    dest_subdivision = tempDataViewModel.dest_subdivision,
                                    dest_country = tempDataViewModel.dest_country,
                                    distance = tempDataViewModel.distance,
                                    notes = tempDataViewModel.tripNote
                                )
                            )
                        } else {
                            if (editPointIndex != null) {
                                EditTripPoint(travelManager,tempDataViewModel,startDate,endDate,startLoc,endLoc,tripName,editPointIndex,outputFormat)
                            }
                        }
                    }
                    tempDataViewModel.lastLatitude = tempDataViewModel.dest_latitude.toDouble()
                    tempDataViewModel.lastLongitude = tempDataViewModel.dest_longtitude.toDouble()
                    tempDataViewModel.lastDestCityName = tempDataViewModel.dest_city_name
                    clearTripPointData(tempDataViewModel)
                    navController.popBackStack()
                    }
            ) {
                if(editPointIndex == -1) {
                    Text("Add")
                } else {
                    Text("Edit")
                }
            }
        }
    }
}

@Composable
fun tripInfoUiState(navigationUiState: NavigationUiState, tempDataViewModel: TempDataViewModel) {
    when(navigationUiState) {
        is NavigationUiState.NoRequest -> Text("Loading...")
        is NavigationUiState.Success -> TripInfoCard(navigationUiState.route,tempDataViewModel)
        is NavigationUiState.Error -> ErrorCard()
    }
}

@Composable
fun ErrorCard() {
    Card(
        shape = RoundedCornerShape(CornerSize(10.dp)),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer,
            disabledContentColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onErrorContainer,
            disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
    ) {
        Text(
            modifier = Modifier.padding(12.dp),
            text = "Error getting data from API"
        )
    }
}

// \/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
// \/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
//                          HOTEL POINT
// \/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
// \/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

@Composable
fun AddHotelPoint(navController: NavController, tempDataViewModel: TempDataViewModel, modifier: Modifier, travelManager: Travel_Point_Manager, tripName: String?, editPointIndex: Int? = -1) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    LaunchedEffect(currentBackStackEntry.value) {
        if(!tempDataViewModel.redirectFromBrowser) {
            if (editPointIndex != -1 && tripName != null && editPointIndex != null) {
                getEditedTripPointData(travelManager, tempDataViewModel, tripName, editPointIndex)
            }
        }
    }
    Column {
        HotelInputSection(navController,tempDataViewModel,modifier,travelManager, tripName, editPointIndex)
        DateTimeInputSection(tempDataViewModel,modifier)
        HotelSuggestionsSection(tempDataViewModel = tempDataViewModel,
            modifier = modifier,
            cityName = "city name",
            navController = navController)
    }
}

@Composable
fun POIsUiState(tempDataViewModel: TempDataViewModel, uiState: PlacesUiState, navController: NavController, modifier: Modifier, mode: String = "row", searchType: String = "Hotel") {
    when (uiState) {
        is PlacesUiState.NoRequest -> EmptyScreen()
        is PlacesUiState.Success -> if(searchType == "Hotel") { HotelCards(tempDataViewModel,uiState.searchResponse, navController, modifier, mode) }
        else { AttractionCards(tempDataViewModel,uiState.searchResponse, navController, modifier, mode) }
        is PlacesUiState.Error -> ErrorScreen(modifier)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddAttractionPoint(navController: NavController, tempDataViewModel: TempDataViewModel, modifier: Modifier, travelManager: Travel_Point_Manager, tripName: String?, editPointIndex: Int? = -1) {
    if(editPointIndex != -1 && tripName != null && editPointIndex != null) {
        getEditedTripPointData(travelManager, tempDataViewModel, tripName, editPointIndex)
    }
        AttractionInput(navController, tempDataViewModel, modifier, travelManager, tripName, editPointIndex)
}