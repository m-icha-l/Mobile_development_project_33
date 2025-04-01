package com.example.travel_buddy

import Travel_Point_Manager
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travel_buddy.classes_res.Travel_point
import com.example.travel_buddy.classes_res.dbTravel_point
import androidx.compose.foundation.lazy.items


import com.example.travel_buddy.classes_res.heritage_points.Attraction_point
import com.example.travel_buddy.classes_res.heritage_points.Hotel_point
import com.example.travel_buddy.classes_res.heritage_points.Trip_point

import com.example.travel_buddy.functions.exportToPdf

import com.example.travel_buddy.ui.theme.Travel_BuddyTheme
import com.example.travel_buddy.viewmodel.DataEntryViewModel
import android.util.Log
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
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var travelPointViewModel: DataEntryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        travelPointViewModel = ViewModelProvider(this).get(DataEntryViewModel::class.java)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
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
                        dataEntryViewModel = travelPointViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )*/
                }
            }
        }
        travelPointViewModel = ViewModelProvider(this).get(DataEntryViewModel::class.java)

        travelPointViewModel.allTravelPoints.observe(this, { travelPoints ->
            // Update UI with list of travel points
        })

        val newTravelPoint = Travel_point(name = "Santa Claus Village")
        travelPointViewModel.insertTravelPoint(newTravelPoint)
    }
}

