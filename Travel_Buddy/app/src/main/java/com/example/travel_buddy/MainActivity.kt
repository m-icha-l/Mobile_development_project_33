package com.example.travel_buddy


import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.travel_buddy.classes_res.Date
import com.example.travel_buddy.classes_res.Duration
import com.example.travel_buddy.classes_res.Travel_Point_Manager
import com.example.travel_buddy.classes_res.heritage_points.Attraction_point
import com.example.travel_buddy.classes_res.heritage_points.Hotel_point
import com.example.travel_buddy.classes_res.heritage_points.Trip_point
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
                    //Temporary for testing purposes
                    var travelManager = Travel_Point_Manager(travelPointViewModel)
                    val names = travelManager.display_all_trips()
                    if(names == null) {
                        var name = "Hotel Point"
                        var date = Date("01/05/2025 10:20")
                        var end_date = Date("06/05/2025 17:20")
                        var notes = "Hotel *****"
                        var location = null
                        val neighborhood = "neighborhood"
                        val phone = "phone"
                        val url = "url"
                        val freeFormAddress = "freeFormAddress"
                        var newId = 0
                        var travelPlanName = "Demo travel plan"
                        var time: Duration = end_date - date
                        val city = "Oulu"
                        val newHotel = Hotel_point(
                            name,
                            city,
                            date,
                            end_date,
                            time,
                            location,
                            neighborhood,
                            phone,
                            url,
                            freeFormAddress,
                            newId,
                            travelPlanName,
                            notes
                        )

                        name = "Attraction Point"
                        date = Date("02/05/2025 14:20")
                        end_date = Date("02/05/2025 16:20")
                        notes = "Cathedral"
                        var meetingPoint = "Entrance"

                        val newAttra = Attraction_point(
                            name = name,
                            date = date,
                            end_date = end_date,
                            time = time,
                            location = location,
                            travel_plan_name =  travelPlanName,
                            city = city,
                            neighborhood = neighborhood,
                            meetingPoint = meetingPoint
                        )

                        /*
                            name: String = "No name point",
                            date: Date = Date(),
                            end_date: Date = date,
                            time: Duration = end_date - date,
                            location: Location? = null,
                            newId: Int = 0, // Nowe pole ID
                            travel_plan_name: String = "No travel plan name",
                            var city: String = "",
                            var neighborhood: String = "",
                            var phone: String = "",
                            var freeFormAddress: String = "",
                            var meetingPoint: String = "",
                            notes: String = ""
                        * */

                        name = "Trip Point"
                        date = Date("01/05/2025 10:10")
                        end_date = Date("01/05/2025 15:15")
                        notes = "Stop in Jyvaskyla"
                        var start_subdivision = "No subdivision"
                        var start_country = "Finland"
                        var dest_name = "Oulu"
                        var dest_subdivision = "No subdivision"
                        var dest_country = "Finland"
                        var distance = "608"
                        var urlToPhoto = ""
                        val newTrip = Trip_point(
                            newId,
                            name,
                            location,
                            date,
                            end_date,
                            time,
                            location,
                            travelPlanName,
                            notes,
                            start_subdivision,
                            start_country,
                            dest_name,
                            dest_subdivision,
                            dest_country,
                            distance,
                            urlToPhoto
                        )
                        travelManager.add_Point(travelPlanName, newHotel)
                        travelManager.add_Point(travelPlanName, newAttra)
                        travelManager.add_Point(travelPlanName, newTrip)

                    }
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