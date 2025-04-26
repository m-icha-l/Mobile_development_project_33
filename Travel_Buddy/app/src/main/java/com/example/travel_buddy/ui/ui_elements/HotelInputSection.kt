package com.example.travel_buddy.ui.ui_elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.LocationCity
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.travel_buddy.R
import com.example.travel_buddy.viewmodel.TempDataViewModel

@Composable
fun HotelInputSection(navController: NavController, tempDataViewModel: TempDataViewModel, modifier: Modifier) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        OutlinedTextField(
            value = tempDataViewModel.selectedHotel,
            onValueChange = {  },
            label = { Text("Hotel point") },
            placeholder = { Text("Click \"+\" add point") },
            modifier = Modifier
                .weight(1f),
            readOnly = true,
            singleLine = true,
            leadingIcon = {
                Icon(Icons.Default.LocationOn, contentDescription = "StartLocation")
            },
            trailingIcon = {
                Icon(Icons.Default.Close, contentDescription = "Clear")
            }
        )

        Spacer(modifier = Modifier.width(8.dp)) // Add space between the TextField and the button

        Surface(
            color = if (isSystemInDarkTheme()) Color(0xFF1E1D6D) else Color(0xFF6562DF),
            shape = CircleShape,
            modifier = Modifier
                .size(54.dp)
                .clickable(onClick = {
                    navController.navigate("HotelBrowser")
                })
        ) {
            Image(
                painter = painterResource(id = R.drawable.plus_icon),
                contentDescription = "Add",
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(
                        scaleX = 0.5f,
                        scaleY = 0.5f
                    )
            )
        }
    }
    if(tempDataViewModel.freeFormAddress != "") {
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = "Address: ${tempDataViewModel.freeFormAddress}")
    }
}