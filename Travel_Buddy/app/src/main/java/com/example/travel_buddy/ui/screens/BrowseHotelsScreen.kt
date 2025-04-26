package com.example.travel_buddy.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.travel_buddy.viewmodel.TempDataViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowseHotelsScreen(navController: NavController,tempDataViewModel: TempDataViewModel,modifier: Modifier) {
    var searchQuery by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(start = 12.dp, top = 2.dp, end = 12.dp, bottom = 12.dp)
                .fillMaxWidth(),
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            onSearch = { active = false
                tempDataViewModel.getPlacesList("poiSearch",searchQuery,10,categorySet="7314")},
            active = active,
            onActiveChange = { active = it },

            placeholder = {Text("Search for hotels...")},

            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            },
            trailingIcon = {
                if (active)
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = null
                    )
            },
            colors = SearchBarDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            ),
            tonalElevation = 3.dp,
        )
        {
            POIsUiState(tempDataViewModel,tempDataViewModel.placesUiState,navController, modifier, "column")
        }
    }
}