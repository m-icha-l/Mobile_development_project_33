package com.example.travel_buddy.ui.screens

import android.location.Location
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.travel_buddy.classes_res.Date
import com.example.travel_buddy.classes_res.Travel_Point_Manager
import com.example.travel_buddy.classes_res.Travel_point
import com.example.travel_buddy.viewmodel.DataEntryViewModel
import com.example.travel_buddy.viewmodel.TempDataViewModel

@Composable
fun TravelsScreen(viewModel: DataEntryViewModel, navController: NavController, tempDataViewModel: TempDataViewModel, modifier: Modifier)
{
    var index = 1
    DataEntryViewModel.TopBarName.updateText(index.toString())

    var travelManager = Travel_Point_Manager(viewModel)

    val name = "New point"
    val date = Date("01/05/2025 15:20")
    val end_date = Date("01/05/2025 15:20")
    var notes: String = ""
    var location: Location? = null
    var newId = 0
    var travelPlanName = "New travel plan"
    val newPoint = Travel_point(name, date, end_date, null ,location,"",newId, travelPlanName)

    val names = travelManager.display_all_trips()

    Column(
        modifier = modifier
    ){
        if(names == null)

        {
            TravelPlanItem("No travel plans available")
            {
                travelManager.add_Point(travelPlanName, newPoint)
            }
        }
        else
        {

            LazyColumn{
                items (names){ name ->
                    TravelPlanItem(name.trip_name)
                    {
                        tempDataViewModel.lastTravel = name.trip_name
                        navController.navigate("DetailsScreen/${name.trip_name}")
                    }
                }
            }
        }
    }

}

@Composable
fun TravelPlanItem(name : String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = name, style = MaterialTheme.typography.bodyLarge)
//                Text(text = stringResource(R.string.lorem_ipsum), style = MaterialTheme.typography.bodyMedium)
            }
        }
    }

}