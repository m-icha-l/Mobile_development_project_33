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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import android.app.DatePickerDialog
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
import com.example.travel_buddy.R
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddPointScreen(viewModel: DataEntryViewModel, navController: NavController, tempDataViewModel: TempDataViewModel, modifier: Modifier, type: String?)
{
    when (type) {
        "Trip Point" -> AddTripPoint(navController,tempDataViewModel,modifier)
        "Hotel Point" -> AddHotelPoint(navController,tempDataViewModel,modifier)
        "Attraction Point" -> AddAttractionPoint(navController,tempDataViewModel,modifier)
        else -> Text (
            modifier = modifier,
            text = "Error"
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePickerModal() {
    val context = LocalContext.current
    var selectedDateTime by remember { mutableStateOf<LocalDateTime?>(null) }

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
                    selectedDateTime = date.atTime(selectedHour, selectedMinute)
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true // 24-hour format
            ).show()
        }
        }


/*
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    showDatePicker = false
                    datePickerState.selectedDateMillis?.let {
                        val selectedDate = Instant.ofEpochMilli(it)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        timePicker(selectedDate)
                    }
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            DatePicker(
                state = datePickerState,
                title = { Text("Select date", fontWeight = FontWeight.Normal) }
            )
        }
    }

 */

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
            value = selectedDateTime?.format(displayFormatter) ?: "",
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddTripPoint(navController: NavController, tempDataViewModel: TempDataViewModel, modifier: Modifier) {

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
        verticalArrangement = Arrangement.spacedBy(16.dp)
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

        DateTimePickerModal()

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
fun AddHotelPoint(navController: NavController, tempDataViewModel: TempDataViewModel, modifier: Modifier) {
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

@Composable
fun AddAttractionPoint(navController: NavController, tempDataViewModel: TempDataViewModel, modifier: Modifier) {
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