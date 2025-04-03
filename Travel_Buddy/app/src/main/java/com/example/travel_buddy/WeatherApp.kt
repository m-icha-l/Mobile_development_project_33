import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.travel_buddy.viewmodel.WeatherViewModel
import com.example.travel_buddy.ui.screens.HourlyForecastScreen
import com.example.travel_buddy.ui.screens.WeatherScreen

@Composable
fun WeatherApp(viewModel: WeatherViewModel, navController: NavHostController) {
    NavHost(navController = navController, startDestination = "weatherScreen") {
        composable("weatherScreen") { WeatherScreen(viewModel, navController) }
        composable(
            "hourly_forecast/{dayIndex}",
            arguments = listOf(navArgument("dayIndex") { type = NavType.IntType })
        ) { backStackEntry ->
            val dayIndex = backStackEntry.arguments?.getInt("dayIndex") ?: 0
            HourlyForecastScreen(viewModel, navController, dayIndex)
        }
    }
}
