package com.example.travel_buddy.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.travel_buddy.ui.screens.AddPointScreen
import com.example.travel_buddy.ui.screens.BrowseCitiesScreen
import com.example.travel_buddy.ui.screens.CurrentWeatherScreen
import com.example.travel_buddy.ui.screens.DetailsScreen
import com.example.travel_buddy.ui.screens.HourlyForecastScreen
import com.example.travel_buddy.ui.screens.SettingsScreen
import com.example.travel_buddy.ui.screens.TravelsScreen
import com.example.travel_buddy.viewmodel.DataEntryViewModel
import com.example.travel_buddy.viewmodel.TempDataViewModel


@Composable
fun MainApp(viewModel: DataEntryViewModel, navController: NavHostController, tempDataViewModel: TempDataViewModel, modifier: Modifier = Modifier)
{
    NavHost(navController = navController, startDestination = "TravelsScreen")
    {
        composable("TravelsScreen") { TravelsScreen(viewModel, navController, tempDataViewModel, modifier) }
        composable("SettingsScreen") { SettingsScreen(viewModel, navController, tempDataViewModel, modifier) }
        composable("CurrentWeatherScreen") { CurrentWeatherScreen(viewModel, navController, tempDataViewModel, modifier) }
        composable(
            "DetailsScreen/{Index}",
            arguments = listOf(navArgument("Index") { type = NavType.IntType })
        ) { backStackEntry ->
            val Index = backStackEntry.arguments?.getInt("Index") ?: 0
            DetailsScreen(viewModel, navController, tempDataViewModel, modifier, Index)
        }
        composable("AddPointScreen/{type}",
            arguments = listOf(navArgument("type") { type = NavType.StringType })
            ) { AddPointScreen(viewModel, navController, tempDataViewModel, modifier,it.arguments?.getString("type")) }
        composable("Browser/{type}",
            arguments = listOf(navArgument("type") { type = NavType.StringType })
        ) { BrowseCitiesScreen(viewModel, navController, tempDataViewModel, modifier,it.arguments?.getString("type")) }
    }
}

