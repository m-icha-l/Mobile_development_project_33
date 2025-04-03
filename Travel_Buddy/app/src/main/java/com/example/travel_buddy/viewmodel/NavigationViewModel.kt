package com.example.travel_buddy.viewmodel

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travel_buddy.classes_res.model.DirectionsApi
import com.example.travel_buddy.classes_res.model.RouteResponse
import kotlinx.coroutines.launch
import java.util.Objects

interface NavigationUiState {
    data class Success(val route: RouteResponse): NavigationUiState
    object Error: NavigationUiState
    object NoRequest: NavigationUiState
}


class NavigationViewModel: ViewModel()
{
    var navigationUiState: NavigationUiState by
    mutableStateOf<NavigationUiState>(NavigationUiState.NoRequest) //all data fetched from API is stored here (if succeeded)
        private set
    var counter by mutableStateOf(0)
    val MAX_REQUESTS = 20 //This is only for development to prevent api calls spam on accidental infinite loops

    fun getRoutesList(startLocation: String, EndLocation: String,language:String,canShowTrafficDelays: Boolean,type: String) {
        val locations: String = startLocation + ":" + EndLocation
        viewModelScope.launch {
            var directionsApi: DirectionsApi? = null
            try {
                if(counter < MAX_REQUESTS) {
                    directionsApi = DirectionsApi.getInstance()
                    navigationUiState = NavigationUiState.Success(
                        directionsApi.getRoute(locations, language, canShowTrafficDelays, type)
                    )
                    Log.d("COUNTER", counter.toString())
                } else {
                    Log.d("ERROR", "Too much API calls")
                }
            } catch (e: Exception) {
                Log.d("ERROR",e.message.toString())
                navigationUiState = NavigationUiState.Error
            }
        }
    }
}