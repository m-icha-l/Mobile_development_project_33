package com.example.travel_buddy.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.travel_buddy.viewmodel.DataEntryViewModel

@Composable
fun CurrentWeatherScreen(viewModel: DataEntryViewModel, navController: NavController, modifier: Modifier = Modifier)
{
    Text("Current Weather Screen", modifier = modifier)
}