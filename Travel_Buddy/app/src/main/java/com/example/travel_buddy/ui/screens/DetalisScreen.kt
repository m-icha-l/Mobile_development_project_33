package com.example.travel_buddy.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.travel_buddy.viewmodel.DataEntryViewModel

@Composable
fun DetailsScreen(viewModel: DataEntryViewModel, navController: NavController, modifier: Modifier = Modifier)
{
/*    IconButton(onClick = { navController.popBackStack() }, modifier = modifier) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back"
        )
    }*/
    Card (
        modifier = modifier.clickable{ navController.navigate("TravelsScreen")}
    ){
        Text("Details Screen", modifier = Modifier.padding(4.dp))
    }

}