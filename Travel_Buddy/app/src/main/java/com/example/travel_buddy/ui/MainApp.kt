package com.example.travel_buddy.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.travel_buddy.classes_res.Travel_Point_Manager
import com.example.travel_buddy.ui.screens.AddPointScreen
import com.example.travel_buddy.ui.screens.BrowseCitiesScreen
import com.example.travel_buddy.ui.screens.BrowsePOIsScreen
import com.example.travel_buddy.ui.screens.DetailsScreen
import com.example.travel_buddy.ui.screens.HourlyForecastScreen
import com.example.travel_buddy.ui.screens.PointDetailsScreen
import com.example.travel_buddy.ui.screens.SettingsScreen
import com.example.travel_buddy.ui.screens.TravelsScreen
import com.example.travel_buddy.ui.screens.WeatherScreen
import com.example.travel_buddy.viewmodel.DataEntryViewModel
import com.example.travel_buddy.viewmodel.TempDataViewModel
import com.example.travel_buddy.viewmodel.WeatherViewModel


@Composable
fun MainApp(viewModel: DataEntryViewModel, weatherViewModel: WeatherViewModel, navController: NavHostController, tempDataViewModel: TempDataViewModel, modifier: Modifier = Modifier)
{
    var travelManager = Travel_Point_Manager(viewModel)
    NavHost(navController = navController, startDestination = "TravelsScreen")
    {
        composable("TravelsScreen") { TravelsScreen(viewModel, navController, tempDataViewModel, modifier, travelManager) }
        composable("SettingsScreen") { SettingsScreen(viewModel, navController, tempDataViewModel, modifier) }
        composable(
            "DetailsScreen/{Index}",
            arguments = listOf(navArgument("Index") { type = NavType.StringType })
        ) { backStackEntry ->
            val Index = backStackEntry.arguments?.getString("Index") ?: null
            DetailsScreen(viewModel, navController, tempDataViewModel, modifier, travelManager, Index)
        }
        composable("weatherScreen") { WeatherScreen(weatherViewModel, navController, modifier) }
        composable(
            "hourly_forecast/{dayIndex}",
            arguments = listOf(navArgument("dayIndex") { type = NavType.IntType })
        ) { backStackEntry ->
            val dayIndex = backStackEntry.arguments?.getInt("dayIndex") ?: 0
            HourlyForecastScreen(weatherViewModel, navController, dayIndex, modifier)
        }

        composable("AddPointScreen/{type}/{tripName}/{editPointIndex}",
            arguments = listOf(navArgument("type") { type = NavType.StringType },
                navArgument("tripName") { type = NavType.StringType },
                navArgument("editPointIndex") { type = NavType.IntType})
            ) { AddPointScreen(viewModel, navController, tempDataViewModel, modifier,travelManager,it.arguments?.getString("type"),it.arguments?.getString("tripName"),it.arguments?.getInt("editPointIndex")) }
        composable("Browser/{type}",
            arguments = listOf(navArgument("type") { type = NavType.StringType })
        ) { BrowseCitiesScreen(viewModel, navController, tempDataViewModel, modifier,it.arguments?.getString("type")) }

        composable("PointDetailsScreen/{tripName}/{pointIndex}/{pointType}",
            arguments = listOf(
                navArgument("tripName") { type = NavType.StringType },
                navArgument("pointIndex") { type = NavType.IntType } ,
                navArgument("pointType") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val tripName = backStackEntry.arguments?.getString("tripName")
            val pointIndex = backStackEntry.arguments?.getInt("pointIndex")
            //val pointType = backStackEntry.arguments?.getInt("pointType")
            PointDetailsScreen(viewModel, navController, tempDataViewModel, modifier, tripName, pointIndex)
        }
        composable("POIsBrowser/{searchType}",
            arguments = listOf(navArgument("searchType") { type = NavType.StringType}))
        { BrowsePOIsScreen(navController,tempDataViewModel,modifier,it.arguments?.getString("searchType")) }
    }
}

