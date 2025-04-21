package com.example.travel_buddy.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.travel_buddy.R
import com.example.travel_buddy.TestDisplay
import com.example.travel_buddy.classes_res.Travel_Point_Manager
import com.example.travel_buddy.viewmodel.DataEntryViewModel

@Composable
fun TravelsScreen(viewModel: DataEntryViewModel, navController: NavController, modifier: Modifier)
{
    var index = 1
    DataEntryViewModel.TopBarName.updateText(index.toString())

    var travelManager = Travel_Point_Manager(viewModel)
    val names = travelManager.display_all_trips()

    Column(
        modifier = modifier
    ){
/*        Card (
            modifier = modifier.clickable{ navController.navigate("DetailsScreen")}
        ) {
            Text("Travels Screen", modifier = Modifier.padding(4.dp))

        }*/

        if(names == "No travel plans available")
        {
            TravelPlanItem(names.toString()){}
        }
        else
        {
            for (name in names) {
                index++
                TravelPlanItem(name.toString())
                {
                    navController.navigate("DetailsScreen/$name")
                }
            }
        }


        Log.d("Travel names: ", names)
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