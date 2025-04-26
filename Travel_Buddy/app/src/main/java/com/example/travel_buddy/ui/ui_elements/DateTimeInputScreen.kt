package com.example.travel_buddy.ui.ui_elements

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travel_buddy.viewmodel.TempDataViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import androidx.core.net.toUri

@Composable
fun DateTimeInputSection(tempDataViewModel: TempDataViewModel,modifier: Modifier) {

    val context = LocalContext.current
    val url = "https://www.google.pl/maps/search/hotel/@${tempDataViewModel.lastLatitude},${tempDataViewModel.lastLongitude},20840m"

    Column(
        modifier = Modifier.fillMaxWidth(),
        //verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Check-in date/time
            DateTimeInput(
                tempDataViewModel = tempDataViewModel,
                modifier = Modifier.weight(1f),
                label = "Enter check-in time",
                checkIn = true
            )

            // Check-out date/time
            DateTimeInput(
                tempDataViewModel = tempDataViewModel,
                modifier = Modifier.weight(1f),
                label = "Enter check-out time",
                checkIn = false
            )
        }

        // Open map button
        Button(
            onClick = { val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                context.startActivity(intent) },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(24.dp)),
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
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Open map",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateTimeInput(
    tempDataViewModel: TempDataViewModel,
    checkIn: Boolean,
    modifier: Modifier = Modifier,
    label: String
) {

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
                    if(checkIn) {
                        tempDataViewModel.checkInDateTime = date.atTime(selectedHour, selectedMinute)
                    }
                    else {
                        tempDataViewModel.checkOutDateTime = date.atTime(selectedHour, selectedMinute)
                    }
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
        modifier = modifier
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .clickable { datePickerDialog.show() },
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = Color.Transparent
                    )
                    .padding(start = 6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Calendar",
                    tint = Color.Black
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "date",
                    fontSize = 10.sp,
                    color = Color.Gray
                )
                Row {
                    Text(
                        text =
                            if(checkIn) { if(tempDataViewModel.checkInDateTime != null) { tempDataViewModel.checkInDateTime.toString().replace('T',' ') } else { "yyyy-mm-dd    hh:mm" } }
                            else { if(tempDataViewModel.checkOutDateTime != null) { tempDataViewModel.checkOutDateTime.toString().replace('T',' ') } else { "yyyy-mm-dd    hh:mm" } },
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.primary,
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        )
                    )
                }
            }
        }

        Text(
            text = label,
            fontSize = 10.sp,
            color = Color.Gray,
            modifier = Modifier.align(Alignment.Start)
                .padding(start = 6.dp),
        )
    }
}