package com.example.travel_buddy.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.travel_buddy.viewmodel.DataEntryViewModel
import kotlinx.coroutines.launch

@Composable
fun DrawerApp(viewModel: DataEntryViewModel, navController: NavHostController, modifier: Modifier) {
    var drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column (
                    modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                )
                {
                    Box{
                        Card(
                            Modifier
                                .padding(vertical = 8.dp)
                                .clickable{
                                    scope.launch {
                                        drawerState.close()
                                    }
                                    navController.navigate("TravelsScreen")
                                }
                        ) {
                            Text(
                                text = "Travels",
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 16.dp).fillMaxWidth(),
                                fontSize = 18.sp
                            )
                        }
                    }
                    Box{
                        Card(
                            Modifier
                                .padding(vertical = 8.dp)
                                .clickable{
                                scope.launch {
                                    drawerState.close()
                                }
                                navController.navigate("CurrentWeatherScreen")
                            }
                        ) {
                            Text(
                                text = "Current Weather",
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 16.dp).fillMaxWidth(),
                                fontSize = 18.sp
                            )
                        }
                    }
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ){
                        Card(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 16.dp)
                                .clickable{
                                    scope.launch {
                                        drawerState.close()
                                    }
                                        navController.navigate("SettingsScreen")
                                }
                        ) {
                            Text(
                                text = "Settings",
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 16.dp).fillMaxWidth(),
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
        },
        gesturesEnabled = true
    ) {
        Scaffold(
            topBar = { TopAppBar(drawerState) },
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            MainApp(
                viewModel,
                navController,
                modifier.padding(innerPadding)
            )
        }
    }
}