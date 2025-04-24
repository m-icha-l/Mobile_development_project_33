package com.example.travel_buddy.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.travel_buddy.viewmodel.DataEntryViewModel
import com.example.travel_buddy.viewmodel.TempDataViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(navController: NavController, drawerState: DrawerState, tempDataViewModel: TempDataViewModel) {
    val scope = rememberCoroutineScope()
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = navBackStackEntry?.destination?.route
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
/*        title = {


        },*/
        title = {
            Log.d("Current_location", currentRoute.toString())
            var text = when (currentRoute) {
                "TravelsScreen" -> "Your Travels"
                "CurrentWeatherScreen" -> "Weather Forecast"
                "SettingsScreen" -> "Settings"
                "weatherScreen" -> "Weather forecast"
                "hourly_forecast/{dayIndex}" -> "Hourly forecast"
                "DetailsScreen/{Index}" -> {
                    Log.d("TOP BAR NAME", tempDataViewModel.lastTravel)
                    tempDataViewModel.lastTravel
                }
                "AddPointScreen/{type}/{tripName}" -> "Add " + tempDataViewModel.text
                "PointDetailsScreen/{tripName}/{pointIndex}" -> {
                    Log.d("TOP BAR NAME", tempDataViewModel.lastTravel)
                    tempDataViewModel.lastTravel
                }
                "Browser/{type}" -> tempDataViewModel.browserType
                else -> {
                    Log.d("ELSE","else switch")
                    "Travel Buddy"
                }

            }
            Text(
                text = text,
                color = Color.White
            )
        },
        navigationIcon = {

            IconButton(onClick = {
                scope.launch {
                    drawerState.apply {
                        if (isClosed) open() else close()
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = null
                )
            }

            if (currentRoute == "DetailsScreen/{Index}" ||
                currentRoute == "hourly_forecast/{dayIndex}" ||
                currentRoute == "Browser/{type}"
            ) {
                Box(
                    modifier = Modifier.padding(start = 40.dp)
                )
                {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }

                }
            }
            if (currentRoute == "PointDetailsScreen/{tripName}/{pointIndex}") {
                Box(
                    modifier = Modifier.padding(start = 40.dp)
                )
                {
                    IconButton(onClick = {
                        tempDataViewModel.lastTravel =
                            tempDataViewModel.lastTravel.substringBefore("->").trim()
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            }
        },
        actions = {
            //to do: change to actually edit when possible
            if(currentRoute == "PointDetailsScreen/{tripName}/{pointIndex}"){
                IconButton(onClick = {
                    tempDataViewModel.lastTravel = tempDataViewModel.lastTravel.substringBefore("->").trim()
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit"
                    )
                }
            }
            if(currentRoute == "AddPointScreen/{type}/{tripName}"){
                IconButton(onClick = {  }) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "Idk"
                    )
                }
            }
        }

    )
}