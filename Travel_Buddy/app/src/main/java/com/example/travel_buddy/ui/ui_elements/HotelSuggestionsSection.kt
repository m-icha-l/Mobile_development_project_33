package com.example.travel_buddy.ui.ui_elements

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.travel_buddy.classes_res.model.Address
import com.example.travel_buddy.classes_res.model.TomTomSearchResponse
import com.example.travel_buddy.ui.screens.POIsUiState
import com.example.travel_buddy.viewmodel.TempDataViewModel
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class TriangleShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(size.width / 2f, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        return Outline.Generic(path)
    }
}

@Composable
fun HotelSuggestionsSection(tempDataViewModel: TempDataViewModel,modifier: Modifier,cityName: String, navController: NavController) {
    tempDataViewModel.getPlacesList("categorySearch","hotel",20,lat = tempDataViewModel.lastLatitude, lon = tempDataViewModel.lastLongitude, radius = 20000)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.secondary)
                .fillMaxWidth()
                .height(32.dp)
                .clip(shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Suggested hotels for: ${tempDataViewModel.lastDestCityName}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
        }

        POIsUiState(tempDataViewModel,tempDataViewModel.placesUiState, navController, modifier)
    }
}

@Composable
fun HotelCards(tempDataViewModel: TempDataViewModel, response: TomTomSearchResponse, navController: NavController, modifier: Modifier, mode: String = "row") {
    if(response.results[0].poi != null) {
        if (mode == "row") {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(response.results) { result ->
                    HotelCard(
                        hotelName = result.poi.name,
                        municipality = result.address.municipality,
                        neighborhood = result.address.municipalitySubdivision,
                        score = result.score,
                        phone = result.poi.phone,
                        url = result.poi.url,
                        freeFormAddress = result.address.freeformAddress,
                        lat = result.position.lat,
                        lon = result.position.lon,
                        mode = mode,
                        navController = navController,
                        tempDataViewModel = tempDataViewModel,
                        modifier = Modifier.width(300.dp)
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                items(response.results) { result ->
                    HotelCard(
                        hotelName = result.poi.name,
                        municipality = result.address.municipality,
                        neighborhood = result.address.municipalitySubdivision,
                        score = result.score,
                        phone = result.poi.phone,
                        url = result.poi.url,
                        freeFormAddress = result.address.freeformAddress,
                        lat = result.position.lat,
                        lon = result.position.lon,
                        navController = navController,
                        mode = mode,
                        tempDataViewModel = tempDataViewModel,
                        modifier = Modifier.width(300.dp)
                    )
                }
            }
        }
    } else {
        Text("${response.results[0].address.municipality}")
    }
}

@Composable
private fun HotelCard(
    hotelName: String?,
    municipality: String?,
    neighborhood: String?,
    score: Double?,
    phone: String?,
    url: String?,
    freeFormAddress: String?,
    navController: NavController,
    lat: Double,
    lon: Double,
    mode: String,
    tempDataViewModel: TempDataViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Hotel image placeholder with geometric shapes
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                // Triangle shape
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(TriangleShape())
                        .background(Color.Gray)
                )

                // Square shape
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Gray)
                        .align(Alignment.BottomStart)
                        .padding(start = 16.dp, bottom = 16.dp)
                )

                // Circle shape
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                        .align(Alignment.BottomEnd)
                        .padding(end = 16.dp, bottom = 16.dp)
                )
            }

            // Hotel information
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                if (hotelName != null) {
                    Text(
                        text = hotelName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Text(
                    text = "$municipality, $neighborhood",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(
                                color = Color.Transparent
                            )
                            .padding(start = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Star rating",
                            tint = Color.Black
                        )
                    }
                    Text(
                        text = score.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
                if (phone != null) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(
                                    color = Color.Transparent
                                )
                                .padding(start = 6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = "Star rating",
                                tint = Color.Black
                            )
                        }
                        Text(
                            text = phone,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            val uriHandler = LocalUriHandler.current

            if(!tempDataViewModel.hotelNoteExpanded) {

                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Button(
                        onClick = {
                            if (url != null) {
                                uriHandler.openUri(url)
                            }
                        },
                        modifier = Modifier.border(
                            width = 4.dp,
                            MaterialTheme.colorScheme.primary,
                            RoundedCornerShape(24.dp)
                        ),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowOutward,
                                contentDescription = "Website redirect",
                                modifier = Modifier.size(20.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Website",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }


                    Button(
                        onClick = {
                            if (hotelName != null) {
                                tempDataViewModel.selectedHotel = hotelName
                            }
                            tempDataViewModel.hotelLatitude = lat
                            tempDataViewModel.hotelLongitude = lon
                            if (municipality != null) {
                                tempDataViewModel.hotelMunicipality = municipality
                            }
                            if (neighborhood != null) {
                                tempDataViewModel.hotelNeighborhood = neighborhood
                            }
                            if (phone != null) {
                                tempDataViewModel.hotelPhone = phone
                            }
                            if (url != null) {
                                tempDataViewModel.hotelUrl = url
                            }
                            if (freeFormAddress != null) {
                                tempDataViewModel.hotelFreeFormAddress = freeFormAddress
                            }
                            if (mode == "column") {
                                navController.popBackStack()
                            }
                        },
                        //modifier = Modifier.widthIn(min = 120.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        contentPadding = PaddingValues(
                            horizontal = 12.dp,
                            vertical = 6.dp
                        ), // Small internal padding
                        modifier = Modifier
                            .defaultMinSize(minHeight = 48.dp) // Button height looks professional
                            .padding(horizontal = 4.dp)         // Outer padding between buttons
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add",
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "Choose hotel",
                                fontSize = 12.sp,
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Medium,
                                maxLines = 1, // Force 1 line only
                                overflow = TextOverflow.Clip // (optional) show "..." if too small
                            )
                        }
                    }
                }
            }
        }
    }
}