package com.example.travel_buddy.ui

import android.util.Log
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
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
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
                "DetailsScreen/{Index}" -> {
                    Log.d("TOP BAR NAME", tempDataViewModel.lastTravel)
                    tempDataViewModel.lastTravel
                }
                "AddPointScreen/{type}" -> "Add " + tempDataViewModel.text
                "Browser/{type}" -> tempDataViewModel.browserType
                else -> {
                    Log.d("ELSE","else switch")
                    "Travel Buddy"
                }
            }
            Text(
                text,
            )
        },
        navigationIcon = {
            if(currentRoute == "DetailsScreen/{Index}" || currentRoute == "AddPointScreen/{type}" || currentRoute == "Browser/{type}") {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
                else
            {
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
            }
        },
        actions = {
            //to do: change to actually edit when possible
            if(currentRoute == "DetailsScreen/{Index}"){
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit"
                    )
                }
            }
            if(currentRoute == "AddPointScreen/{type}"){
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