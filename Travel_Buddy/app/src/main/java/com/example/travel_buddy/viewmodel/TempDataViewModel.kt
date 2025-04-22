package com.example.travel_buddy.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travel_buddy.classes_res.model.DirectionsApi
import com.example.travel_buddy.classes_res.model.PlacesApi
import com.example.travel_buddy.classes_res.model.RouteResponse
import com.example.travel_buddy.classes_res.model.TomTomSearchResponse
import kotlinx.coroutines.launch

interface PlacesUiState {
    data class Success(val searchResponse: TomTomSearchResponse): PlacesUiState
    object Error: PlacesUiState
    object NoRequest: PlacesUiState
}

class TempDataViewModel : ViewModel() {
    var text by mutableStateOf("Travel Buddy")
    var startingPoint by mutableStateOf("")
    var destinationPoint by mutableStateOf("")
    var selectedDate by mutableStateOf("")
    var showDatePicker by mutableStateOf(false)
    var lastTravel by mutableStateOf("")
    var browserType by mutableStateOf("")

    var placesUiState: PlacesUiState by
    mutableStateOf<PlacesUiState>(PlacesUiState.NoRequest) //all data fetched from API is stored here (if succeeded)
        private set
    var places_max_requests_counter by mutableStateOf(0)
    val PLACES_MAX_REQUESTS =
        20 //This is only for development to prevent api calls spam on accidental infinite loops

    fun updateText(newText: String) {
        text = newText
        Log.d("text_update", "updatedText:  $text")
    }

    fun getPlacesList(
        searchType: String,
        query: String,
        limit: Int = 10,
        typeahead: Boolean = true,
        entityType: String = "Municipality",
        countrySet: String = "",
        categorySet: String = ""
    ) {
        viewModelScope.launch {
            var placesApi: PlacesApi? = null
            try {
                if (places_max_requests_counter < PLACES_MAX_REQUESTS) {
                    placesApi = PlacesApi.getInstance()
                    placesUiState = PlacesUiState.Success(
                        placesApi.getRoute(
                            searchType,
                            query,
                            limit,
                            typeahead,
                            entityType,
                            countrySet,
                            categorySet
                        )
                    )
                    Log.d("COUNTER", places_max_requests_counter.toString())
                } else {
                    Log.d("ERROR", "Too much API calls")
                }
            } catch (e: Exception) {
                Log.d("ERROR", e.message.toString())
                placesUiState = PlacesUiState.Error
            }
        }
    }

    var start_city_name by mutableStateOf("")
    var start_subdivision by mutableStateOf("")
    var start_country by mutableStateOf("")
    var start_latitude by mutableStateOf("")
    var start_longtitude by mutableStateOf("")
    var start_isSet by mutableStateOf(false)

    var dest_city_name by mutableStateOf("")
    var dest_subdivision by mutableStateOf("")
    var dest_country by mutableStateOf("")
    var dest_latitude by mutableStateOf("")
    var dest_longtitude by mutableStateOf("")
    var dest_isSet by mutableStateOf(false)
}