package com.example.travel_buddy.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.travel_buddy.viewmodel.DataEntryViewModel

@Composable
fun SettingsScreen(viewModel: DataEntryViewModel, navController: NavController, modifier: Modifier = Modifier)
{
        Text("Settings Screen", modifier = modifier)
}