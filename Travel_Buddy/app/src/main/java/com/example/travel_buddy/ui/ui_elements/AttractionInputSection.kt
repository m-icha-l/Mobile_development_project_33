package com.example.travel_buddy.ui.ui_elements

import android.content.Intent
import android.location.Location
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.travel_buddy.R
import com.example.travel_buddy.classes_res.Date
import com.example.travel_buddy.classes_res.Travel_Point_Manager
import com.example.travel_buddy.classes_res.heritage_points.Attraction_point
import com.example.travel_buddy.classes_res.heritage_points.Hotel_point
import com.example.travel_buddy.ui.screens.EditAttractionPoint
import com.example.travel_buddy.ui.screens.EditTripPoint
import com.example.travel_buddy.ui.screens.clearAttractionPointData
import com.example.travel_buddy.viewmodel.NavigationViewModel
import com.example.travel_buddy.viewmodel.TempDataViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AttractionInput(navController: NavController, tempDataViewModel: TempDataViewModel, modifier: Modifier, travelManager: Travel_Point_Manager, tripName: String?, editPointIndex: Int? = -1, navigationViewModel: NavigationViewModel = viewModel()) {
    var noteExpanded: Boolean by remember { mutableStateOf(false)}
    val context = LocalContext.current
    val url = "https://www.google.pl/maps/search/attractions/@${tempDataViewModel.lastLatitude},${tempDataViewModel.lastLongitude},20840m"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 120.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = tempDataViewModel.selectedAttraction,
                onValueChange = { tempDataViewModel.selectedAttraction = it },
                label = { Text("Attraction") },
                placeholder = { Text("Click \"+\" add point") },
                modifier = Modifier
                    .weight(1f),
                readOnly = true,
                singleLine = true,
                leadingIcon = {
                    Icon(Icons.Default.LocationOn, contentDescription = "StartLocation")
                },
                trailingIcon = {
                    if (tempDataViewModel.selectedAttraction.isNotEmpty()) {
                        IconButton(onClick = { tempDataViewModel.selectedAttraction = "" }) {
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
                        navController.navigate("POIsBrowser/Attraction")
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
            OutlinedTextField(
                value = tempDataViewModel.meetingPoint,
                onValueChange = { tempDataViewModel.meetingPoint = it },
                label = { Text("Meeting point") },
                placeholder = { Text("Meeting point address") },
                modifier = Modifier.fillMaxWidth(),
                supportingText = {Text("optional")},
                singleLine = true,
                leadingIcon = {
                    Icon(Icons.Default.LocationOn, contentDescription = "StartLocation")
                },
                trailingIcon = {
                    if (tempDataViewModel.meetingPoint.isNotEmpty()) {
                        IconButton(onClick = { tempDataViewModel.meetingPoint = "" }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear")
                        }
                    }
                }
            )

        AttrDateTimeInputSection(tempDataViewModel)

        AnimatedVisibility(
            visible = (tempDataViewModel.selectedAttraction != "" && tempDataViewModel.selectedAttractionDateTime != null),
            enter = fadeIn(animationSpec = tween(500)) + expandVertically(animationSpec = tween(500)),
            exit = fadeOut(animationSpec = tween(300)) + shrinkVertically(animationSpec = tween(300))
        ) {
            Button(
                modifier = Modifier.padding(start = 8.dp),
                onClick = {
                    val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                    val outputFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                    val attrDate = LocalDateTime.parse(
                        tempDataViewModel.selectedAttractionDateTime.toString()
                            .replace('T', ' '),
                        inputFormat
                    )
                    var attrLoc = Location("provider").apply {
                        latitude = tempDataViewModel.attractionLatitude
                        longitude = tempDataViewModel.attractionLongitude
                    }
                    if (tripName != null) {
                        if(editPointIndex == -1) {
                            travelManager.add_Point(
                                tripName, Attraction_point(
                                    name = tempDataViewModel.selectedAttraction,
                                    location = attrLoc,
                                    date = Date(attrDate.format(outputFormat)),
                                    city = tempDataViewModel.attractionMunicipality,
                                    neighborhood = tempDataViewModel.attrNeighborhood,
                                    phone = tempDataViewModel.attrPhone,
                                    freeFormAddress = tempDataViewModel.attrFreeFormAddress,
                                    meetingPoint = tempDataViewModel.meetingPoint,
                                    travel_plan_name = tripName,
                                    notes = tempDataViewModel.attractionNote
                                )
                            )
                        } else {
                            if (editPointIndex != null) {
                                EditAttractionPoint(travelManager,tempDataViewModel,attrDate,attrLoc,tripName,editPointIndex,outputFormat)
                            }
                        }
                    }
                    clearAttractionPointData(tempDataViewModel)
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

        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {

            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                    context.startActivity(intent)
                },
                modifier = Modifier
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

            //Spacer(modifier.width(8.dp))

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
        }

        AnimatedVisibility(
            visible = noteExpanded,
            enter = fadeIn(animationSpec = tween(500)) + expandVertically(animationSpec = tween(500)),
            exit = fadeOut(animationSpec = tween(300)) + shrinkVertically(animationSpec = tween(300))
        ) {
            TextField(
                value = tempDataViewModel.attractionNote,
                onValueChange = { tempDataViewModel.attractionNote = it },
                supportingText = {Text("Write your note...")},
                maxLines = 2,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 16.dp)
            )
        }

        AttractionSuggestionsSection(tempDataViewModel, modifier, navController)
    }
}