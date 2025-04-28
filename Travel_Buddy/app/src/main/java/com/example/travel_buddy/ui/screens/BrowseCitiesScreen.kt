package com.example.travel_buddy.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import  androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.travel_buddy.classes_res.model.TomTomSearchResponse
import com.example.travel_buddy.ui.theme.Typography
import com.example.travel_buddy.viewmodel.DataEntryViewModel
import com.example.travel_buddy.viewmodel.PlacesUiState
import com.example.travel_buddy.viewmodel.TempDataViewModel

/*
@Composable
fun CityCard(article:Content) {
    val uriHandler = LocalUriHandler.current
    Card(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .fillMaxWidth()
            .clickable { uriHandler.openUri(article.url) },
        shape = RoundedCornerShape(CornerSize(10.dp)),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContentColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            AsyncImage(
                model = article.urlToImage,
                placeholder = painterResource(R.drawable.image_icon),
                error = painterResource(R.drawable.image_icon),
                contentDescription = "Image"
            )
            article.title?.let { Text(text = it,style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.padding(top=6.dp)) }
            article.description?.let { Text(text = it,style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSecondary, modifier = Modifier.padding(top=5.dp, bottom=20.dp)) }
            HorizontalDivider( modifier = Modifier.padding(end=280.dp),thickness = 2.dp, color = MaterialTheme.colorScheme.primary)
            Row {
                article.source.name?.let { Text(text = it, fontSize = 12.sp, color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.padding(top=3.dp, bottom=5.dp)) }
                article.publishedAt?.let { Text(text = it.substring(0, 10), fontSize = 10.sp, color = MaterialTheme.colorScheme.tertiary, modifier = Modifier
                    .padding(top = 3.dp, bottom = 5.dp)
                    .fillMaxWidth(), textAlign = TextAlign.Right) }
            }
        }

    }

    //article.source.id?.let { Log.d("SOURCE ID", it) }
}

 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowseCitiesScreen(
    viewModel: DataEntryViewModel, navController: NavController, tempDataViewModel: TempDataViewModel, modifier: Modifier, type: String?) {
    var expanded by rememberSaveable { mutableStateOf(false) } //will be saved and restored during configuration changes, such as screen rotation etc.
    var searchQuery by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        DockedSearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(start = 12.dp, top = 2.dp, end = 12.dp, bottom = 12.dp)
                .fillMaxWidth(),
            query = searchQuery,
            onQueryChange = { searchQuery = it
                tempDataViewModel.getPlacesList("search", searchQuery, entityType = "Municipality")},
            onSearch = { /*active = false*/
                tempDataViewModel.getPlacesList("search", searchQuery, entityType = "Municipality")},
            active = active,
            onActiveChange = { active = it },

            placeholder = { if(type == "trip_start_point") {
                Text("Search travel destinations...") }
                          else if(type == "trip_travel_destination") {
                                Text("Search travel destinations...")
                          }},

            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            },
            trailingIcon = {
                if (active)
                    IconButton(onClick = {
                        searchQuery = ""
                    }){
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = null
                        )
                    }
            },
            colors = SearchBarDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            ),
            tonalElevation = 3.dp,
        )
        {
            BrowseCitiesUiState(tempDataViewModel,tempDataViewModel.placesUiState,navController,modifier,type)
            /*
            // Display search results in a scrollable column
            LazyColumn(Modifier.verticalScroll(rememberScrollState())) {
                items(searchResults) { result ->
                    ListItem(
                        headlineContent = { Text(result) },
                        modifier = Modifier.animateItem()
                            .clickable {

                                expanded = false
                            }
                            .fillMaxWidth()
                    )
                }
            }

             */
        }

    }
}

@Composable
fun BrowseCitiesUiState(tempDataViewModel: TempDataViewModel,uiState: PlacesUiState, navController: NavController, modifier: Modifier,type: String?) {
    //val modifier = Modifier.padding(top=265.dp)
    when (uiState) {
        is PlacesUiState.NoRequest -> EmptyScreen()
        is PlacesUiState.Success -> CitiesCards(tempDataViewModel,uiState.searchResponse, navController, modifier,type)
        is PlacesUiState.Error -> ErrorScreen(modifier)
    }
}

@Composable
fun CitiesCards(tempDataViewModel: TempDataViewModel,response: TomTomSearchResponse, navController: NavController, modifier: Modifier,type: String?) {
    LazyColumn(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
        items(response.results) { result ->
            if (result.type == "Geography") {
                Card(
                    modifier = Modifier.padding(bottom = 8.dp)
                        .clickable(onClick = {
                            if(type == "trip_start_point") {
                                tempDataViewModel.start_city_name =
                                    result.address.municipality.toString()
                                tempDataViewModel.start_subdivision =
                                    result.address.countrySubdivisionName.toString()
                                tempDataViewModel.start_country = result.address.country.toString()
                                tempDataViewModel.start_latitude = result.position.lat
                                tempDataViewModel.start_longtitude = result.position.lon
                                tempDataViewModel.start_isSet = true
                                navController.popBackStack()
                            }
                            else if (type == "trip_travel_destination") {
                                tempDataViewModel.dest_city_name =
                                    result.address.municipality.toString()
                                tempDataViewModel.dest_subdivision =
                                    result.address.countrySubdivisionName.toString()
                                tempDataViewModel.dest_country = result.address.country.toString()
                                tempDataViewModel.dest_latitude = result.position.lat
                                tempDataViewModel.dest_longtitude = result.position.lon
                                tempDataViewModel.dest_isSet = true
                                navController.popBackStack()
                            }
                        }),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp, focusedElevation = 8.dp,pressedElevation = 12.dp,hoveredElevation = 6.dp,draggedElevation = 10.dp,disabledElevation = 0.dp)
                ) {
                    ListItem(
                        //colors = ListItemColors(containerColor = Color(0xFFB0B0B0)),
                        headlineContent = {
                            result.address.municipality?.let {
                                Text(
                                    text = it,
                                    fontWeight = Typography.titleLarge.fontWeight
                                )
                            }
                        },
                        supportingContent = { Text(result.address.countrySubdivisionName + ", " + result.address.country) },
                        trailingContent = { Text("Click to add") },
                        //supportingContent = { Text("supporting content")},
                        modifier = Modifier.animateItem()
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyScreen() {
/*    Text(
        modifier = Modifier.padding(vertical = 260.dp, horizontal = 10.dp),
        text = "test"
    )*/
}

@Composable
fun ErrorScreen(modifier:Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(CornerSize(10.dp)),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer,
            disabledContentColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onErrorContainer,
            disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
    ) {
        Text(
            modifier = Modifier.padding(12.dp),
            text = "Error retrieving data from API"
        )
    }
}