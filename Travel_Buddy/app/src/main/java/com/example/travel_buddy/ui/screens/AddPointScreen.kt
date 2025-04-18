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
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
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
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar

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
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDateTime by remember { mutableStateOf<LocalDateTime?>(null) }

    val displayFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm")

    // Date picker state
    val datePickerState = rememberDatePickerState()

    // Handle TimePickerDialog after date selection
    fun openTimePicker(date: LocalDate) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        TimePickerDialog(
            context,
            { _, selectedHour, selectedMinute ->
                selectedDateTime = date.atTime(selectedHour, selectedMinute)
            },
            hour,
            minute,
            true // 24-hour format
        ).show()
    }

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
                        openTimePicker(selectedDate)
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
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Pick date and time")
                }
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddTripPoint(navController: NavController, tempDataViewModel: TempDataViewModel, modifier: Modifier) {

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    /*
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            tempDataViewModel.selectedDate = "${month + 1}/$dayOfMonth/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val timePickerDialog = remember {
        { year: Int, month: Int, day: Int ->
            TimePickerDialog(
                context,
                { _, hour, minute ->
                    tempDataViewModel.selectedDate = "${month + 1}/$day/$year $hour:${minute.toString().padStart(2, '0')}"
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        }
    }

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            timePickerDialog(year, month, dayOfMonth)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH),
    )

     */

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = tempDataViewModel.startingPoint,
            onValueChange = { tempDataViewModel.startingPoint = it },
            label = { Text("City") },
            placeholder = { Text("Enter starting point") },
            modifier = Modifier.fillMaxWidth(),
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
        OutlinedTextField(
            value = tempDataViewModel.destinationPoint,
            onValueChange = { tempDataViewModel.destinationPoint = it },
            label = { Text("City") },
            placeholder = { Text("Enter travel destination") },
            modifier = Modifier.fillMaxWidth(),
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

        /*
        Card(
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp),
            colors = CardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                disabledContentColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor = MaterialTheme.colorScheme.secondary),
            shape = RoundedCornerShape(CornerSize(10.dp))
        ) {
            Text("Select date", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(8.dp))
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = tempDataViewModel.selectedDate,
                onValueChange = {},
                enabled = false,
                placeholder = { Text("mm/dd/yyyy") },
                trailingIcon = {
                    IconButton(onClick = { datePickerDialog.show() }) {
                        Icon(Icons.Default.DateRange, contentDescription = "Pick date")
                    }
                },
                modifier = Modifier.fillMaxWidth()
                    .padding(8.dp)
            )
        }

         */

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