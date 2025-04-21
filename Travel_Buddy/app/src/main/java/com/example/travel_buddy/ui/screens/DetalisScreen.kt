package com.example.travel_buddy.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import kotlin.collections.forEachIndexed
import kotlin.text.iterator
import com.example.travel_buddy.ui.ui_elements.Add_btn
import com.example.travel_buddy.viewmodel.DataEntryViewModel
import com.example.travel_buddy.viewmodel.TempDataViewModel

@Composable
fun DetailsScreen(viewModel: DataEntryViewModel, navController: NavController, tempDataViewModel: TempDataViewModel, modifier: Modifier = Modifier, Index: Int = -1)
{
/*    IconButton(onClick = { navController.popBackStack() }, modifier = modifier) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back"
        )
    }
    Card (
        modifier = modifier.clickable{ navController.popBackStack()}
    ){

    }*/

    var travelManager = Travel_Point_Manager(viewModel)
    val points = travelManager.display_trip(Index.toString())

    Text("Details Screen $Index", modifier = Modifier.padding(4.dp))
    if (points == "No travel points found in trip_name: $Index") {
        TravelDetailItem(points.toString())
    } else {
        Column(
            modifier = Modifier.padding(top = 100.dp)
        ) {
            points.forEachIndexed { index, point ->
                TravelDetailItem(point.toString(), tempDataViewModel, navController)

            }
        }
        Text("Details Screen $Index", modifier = Modifier.padding(4.dp))
        TravelDetailItem("", tempDataViewModel, navController)
    }


}

@Composable
fun TravelDetailItem(name: String, tempDataViewModel: TempDataViewModel,navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp),
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
                Text(text = stringResource(R.string.lorem_ipsum), style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
    Add_btn(tempDataViewModel,navController)
}