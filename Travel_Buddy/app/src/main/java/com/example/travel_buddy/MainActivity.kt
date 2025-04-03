package com.example.travel_buddy

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.travel_buddy.classes_res.Travel_point


import com.example.travel_buddy.classes_res.heritage_points.Attraction_point
import com.example.travel_buddy.classes_res.heritage_points.Hotel_point
import com.example.travel_buddy.classes_res.heritage_points.Trip_point

import com.example.travel_buddy.functions.exportToPdf

import com.example.travel_buddy.ui.theme.Travel_BuddyTheme
import com.example.travel_buddy.viewmodel.DataEntryViewModel
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.travel_buddy.ui.DrawerApp
import com.example.travel_buddy.ui.MainApp
import com.example.travel_buddy.viewmodel.WeatherViewModel
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.stringResource
import com.example.travel_buddy.classes_res.Travel_Point_Manager
import com.example.travel_buddy.viewmodel.NavigationUiState
import com.example.travel_buddy.viewmodel.NavigationViewModel
import kotlinx.coroutines.launch
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.Manifest


class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var viewModel: WeatherViewModel
    private lateinit var travelPointViewModel: DataEntryViewModel
    //private lateinit var travelPointViewModel: DataEntryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        //travelPointViewModel = ViewModelProvider(this).get(DataEntryViewModel::class.java)
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]

        enableEdgeToEdge()
        setContent {
                requestLocationPermission { lat, lon ->
                    viewModel.fetchForecast(lat, lon)
                }
            val navController = rememberNavController()
            Travel_BuddyTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                    ) { innerPadding ->
                    DrawerApp(
                        travelPointViewModel,
                        navController,
                        Modifier.padding(innerPadding)
                    )
                    /*TestDisplay(
                        name = "Travel buddy",
                        modifier = Modifier.padding(innerPadding)
                    )*/
                }
            }
        }

        /*
        travelPointViewModel = ViewModelProvider(this).get(DataEntryViewModel::class.java)

        travelPointViewModel.allTravelPoints.observe(this, { travelPoints ->
            // Update UI with list of travel points
        })

        val newTravelPoint = Travel_point(name = "Santa Claus Village")
        travelPointViewModel.insertTravelPoint(newTravelPoint)

         */
    }

    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                getApproximateLocation { lat, lon ->
                    viewModel.fetchForecast(lat, lon)
                }
            } else {
                viewModel.fetchForecast(null, null)
            }
        }

    private fun requestLocationPermission(onLocationReceived: (Double?, Double?) -> Unit) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            getApproximateLocation(onLocationReceived)
        } else {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

    private fun getApproximateLocation(onLocationReceived: (Double?, Double?) -> Unit) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                onLocationReceived(location?.latitude, location?.longitude)
            }
        } else {
            onLocationReceived(null, null)
        }
    }
    val test = Travel_point()
    val test2 = Travel_point()
    val test3 = Travel_point()
    val travel_points = arrayOf(test,test2,test3)
    val context = LocalContext.current
    exportToPdf(context,"testpdf",travel_points)

    TestScreen(navigationViewModel.navigationUiState)

    Button(
        colors = ButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContentColor = MaterialTheme.colorScheme.tertiary,
            disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer
        ),
        modifier = Modifier.padding(top = 400.dp, start = 12.dp, end = 12.dp)
            .fillMaxWidth(),
        onClick = {
            navigationViewModel.counter++
            navigationViewModel.getRoutesList(
                    "52.50931,13.42936",
                "52.50274,13.43872",
                    "en-GB",
                    true,
                "car"
                )
        },
        content = { Text("Test Directions API")}
    )

}

@Composable
fun TestScreen(uiState: NavigationUiState) {
    val modifier = Modifier.padding(top=265.dp)
    when (uiState) {
        is NavigationUiState.NoRequest -> Log.d("LOADING","yes")
        is NavigationUiState.Success -> Log.d("Success", uiState.route.toString())
        is NavigationUiState.Error -> Log.d("ERROR","yes")
    }
}