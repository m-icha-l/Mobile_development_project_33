package com.example.travel_buddy


import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.travel_buddy.ui.DrawerApp
import com.example.travel_buddy.ui.theme.Travel_BuddyTheme
import com.example.travel_buddy.viewmodel.DataEntryViewModel
import com.example.travel_buddy.viewmodel.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var travelPointViewModel: DataEntryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        travelPointViewModel = ViewModelProvider(this).get(DataEntryViewModel::class.java)
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            requestLocationPermission { lat, lon ->
                weatherViewModel.fetchForecast(lat, lon)
            }
            val navController = rememberNavController()
            Travel_BuddyTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    DrawerApp(
                        travelPointViewModel,
                        weatherViewModel,
                        navController,
                        Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                getApproximateLocation { lat, lon ->
                    weatherViewModel.fetchForecast(lat, lon)
                }
            } else {
                weatherViewModel.fetchForecast(null, null)
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

}