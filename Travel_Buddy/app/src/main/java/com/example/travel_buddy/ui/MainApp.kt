package com.example.travel_buddy.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.travel_buddy.ui.screens.DetailsScreen
import com.example.travel_buddy.ui.screens.HourlyForecastScreen
import com.example.travel_buddy.ui.screens.SettingsScreen
import com.example.travel_buddy.ui.screens.TravelsScreen
import com.example.travel_buddy.ui.screens.WeatherScreen
import com.example.travel_buddy.viewmodel.DataEntryViewModel
import com.example.travel_buddy.viewmodel.WeatherViewModel


@Composable
fun MainApp(viewModel: DataEntryViewModel, weatherViewModel: WeatherViewModel, navController: NavHostController, modifier: Modifier = Modifier)
{
    NavHost(navController = navController, startDestination = "TravelsScreen")
    {
        composable("TravelsScreen") { TravelsScreen(viewModel, navController, modifier) }
        composable("SettingsScreen") { SettingsScreen(viewModel, navController, modifier) }
        composable(
            "DetailsScreen/{Index}",
            arguments = listOf(navArgument("Index") { type = NavType.IntType })
        ) { backStackEntry ->
            val Index = backStackEntry.arguments?.getInt("Index") ?: 0
            DetailsScreen(viewModel, navController, modifier, Index)
        }
        composable("weatherScreen") { WeatherScreen(weatherViewModel, navController, modifier) }
        composable(
            "hourly_forecast/{dayIndex}",
            arguments = listOf(navArgument("dayIndex") { type = NavType.IntType })
        ) { backStackEntry ->
            val dayIndex = backStackEntry.arguments?.getInt("dayIndex") ?: 0
            HourlyForecastScreen(weatherViewModel, navController, dayIndex, modifier)
        }

    }
}

