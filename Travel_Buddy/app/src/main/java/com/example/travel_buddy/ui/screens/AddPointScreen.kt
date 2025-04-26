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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.travel_buddy.classes_res.Date
import com.example.travel_buddy.functions.parseLocation
import com.example.travel_buddy.ui.ui_elements.DateTimeInputSection
import com.example.travel_buddy.ui.ui_elements.HotelCards
import com.example.travel_buddy.ui.ui_elements.HotelInputSection
import com.example.travel_buddy.ui.ui_elements.HotelSuggestionsSection
import com.example.travel_buddy.viewmodel.PlacesUiState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddPointScreen(viewModel: DataEntryViewModel, navController: NavController, tempDataViewModel: TempDataViewModel, modifier: Modifier, travelManager: Travel_Point_Manager,type: String?,tripName: String?)
{
    when (type) {
        "Trip Point" -> AddTripPoint(navController,tempDataViewModel,modifier,travelManager, tripName)
        "Hotel Point" -> AddHotelPoint(navController,tempDataViewModel,modifier,travelManager, tripName)
        "Attraction Point" -> AddAttractionPoint(navController,tempDataViewModel,modifier,travelManager, tripName)
        else -> Text (
            modifier = modifier,
            text = "Error"
        )
    }
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
fun AddTripPoint(navController: NavController, tempDataViewModel: TempDataViewModel, modifier: Modifier, travelManager: Travel_Point_Manager, tripName: String?,navigationViewModel: NavigationViewModel = viewModel()) {

    var startText = ""
    if(tempDataViewModel.start_isSet) {
        startText = tempDataViewModel.start_city_name + ", " + tempDataViewModel.start_subdivision + ", " + tempDataViewModel.start_country
    }

    var destText = ""
    if(tempDataViewModel.dest_isSet) {
        destText = tempDataViewModel.dest_city_name + ", " + tempDataViewModel.dest_subdivision + ", " + tempDataViewModel.dest_country
    }

    Column(
        modifier = modifier
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
                value = destText,
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
        if(tempDataViewModel.start_isSet && tempDataViewModel.dest_isSet && tempDataViewModel.selectedDateTime != null) {
            val startLoc: String = tempDataViewModel.start_latitude + "," + tempDataViewModel.start_longtitude
            val endLoc: String = tempDataViewModel.dest_latitude + "," + tempDataViewModel.dest_longtitude
            navigationViewModel.getRoutesList(startLoc,endLoc,"en-GB",true,"car",tempDataViewModel.selectedDateTime.toString() + ":00Z")
            tripInfoUiState(navigationViewModel.navigationUiState,tempDataViewModel)
            Button(
                modifier = Modifier.padding(top=16.dp),
                onClick = {
                    val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                    val outputFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                    val startDate = LocalDateTime.parse(tempDataViewModel.selectedDateTime.toString().replace('T', ' '), inputFormat)
                    val endDate = LocalDateTime.parse(tempDataViewModel.arrivalDate, inputFormat)
                        var startLoc = Location("uiygiy").apply {
                            latitude = tempDataViewModel.start_latitude.toDouble()
                            longitude =  tempDataViewModel.start_longtitude.toDouble() }
                        var endLoc = Location("sdjnsjdk").apply {
                            latitude = tempDataViewModel.start_latitude.toDouble()
                            longitude = tempDataViewModel.start_longtitude.toDouble() }
                    if (tripName != null) {
                        travelManager.add_Point(tripName,Trip_point(
                            name = tempDataViewModel.start_city_name,
                            location = startLoc,
                            end_location = endLoc,
                            date = Date(endDate.format(outputFormat)),
                            end_date = Date(endDate.format(outputFormat)),
                            plan_name = tripName,
                            start_subdivision = tempDataViewModel.start_subdivision,
                            start_country = tempDataViewModel.start_country,
                            dest_name = tempDataViewModel.dest_city_name,
                            dest_subdivision = tempDataViewModel.dest_subdivision,
                            dest_country = tempDataViewModel.dest_country,
                            distance = tempDataViewModel.distance,
                            urlToPhoto = ""
                        ))
                    }
                    tempDataViewModel.lastLatitude = tempDataViewModel.dest_latitude.toDouble()
                    tempDataViewModel.lastLongitude = tempDataViewModel.dest_longtitude.toDouble()
                    navController.popBackStack()
                    }
            ) {
                Text("Add")
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
fun AddHotelPoint(navController: NavController, tempDataViewModel: TempDataViewModel, modifier: Modifier, travelManager: Travel_Point_Manager, tripName: String?) {
    Column {
        HotelInputSection(navController,tempDataViewModel,modifier)
        DateTimeInputSection(tempDataViewModel,modifier)
        HotelSuggestionsSection(tempDataViewModel = tempDataViewModel,
            modifier = modifier,
            cityName = "city name",
            navController = navController)
    }
}

@Composable
fun POIsUiState(tempDataViewModel: TempDataViewModel, uiState: PlacesUiState, navController: NavController, modifier: Modifier, mode: String = "row") {
    when (uiState) {
        is PlacesUiState.NoRequest -> EmptyScreen()
        is PlacesUiState.Success -> HotelCards(tempDataViewModel,uiState.searchResponse, navController, modifier, mode)
        is PlacesUiState.Error -> ErrorScreen(modifier)
    }
}

@Composable
fun AddAttractionPoint(navController: NavController, tempDataViewModel: TempDataViewModel, modifier: Modifier, travelManager: Travel_Point_Manager, tripName: String?) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(0.65f),
                value = tempDataViewModel.startingPoint,
                onValueChange = { tempDataViewModel.startingPoint = it },
                label = { Text(text = "Enter starting point") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
        }
    }
}