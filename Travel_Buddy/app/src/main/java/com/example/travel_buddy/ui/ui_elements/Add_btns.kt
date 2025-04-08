package com.example.travel_buddy.ui.ui_elements

import android.widget.ImageView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travel_buddy.R

@Composable
fun Add_btn() {
    var open_popup by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp) // Zapewnia marginesy
    ) {
        FloatingActionButton(
            onClick = {
                open_popup = true;
            },
            modifier = Modifier
                .size(72.dp)
                .align(Alignment.BottomEnd) // Pozycjonowanie w lewym dolnym rogu
                .border(5.dp,if(isSystemInDarkTheme()) Color(0xFF6562DF) else Color(0xFF1E1D6D), CircleShape),
            containerColor = if(isSystemInDarkTheme()) Color(0xFF1E1D6D) else Color(0xFF6562DF), // Dopasowanie koloru
            shape = CircleShape
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
    adder_opup(open_popup,{open_popup = false},"works")
}

@Composable
fun adder_opup(isDialogOpen: Boolean, onDismiss: () -> Unit, text: String) {
    var selectedPoint by remember { mutableStateOf("") }
    if (isDialogOpen) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Mood Radio", style = MaterialTheme.typography.titleLarge) },
            text = {
                Text(text)
                Spacer(modifier = Modifier.height(24.dp))

                TravelPointDropdown(
                    selectedOption = selectedPoint,
                    onOptionSelected = { selectedPoint = it }
                )
                   },
            confirmButton = {
                Button(onClick = onDismiss) {
                    Text("OK")
                }
            },
            modifier = Modifier,
            shape = MaterialTheme.shapes.medium
        )
    }
}

@Composable
fun TravelPointDropdown(
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val options = listOf("Custom Point", "Trip Point", "Hotel Point", "Attraction Point")
    var expanded by remember { mutableStateOf(false) }
    var buttonWidth by remember { mutableStateOf(0) }

    Column(modifier = modifier.padding(16.dp)) {
        Button(
            onClick = { expanded = !expanded },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2C237B),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .onGloballyPositioned { coordinates ->
                    // Capture the width of the button in pixels
                    buttonWidth = coordinates.size.width
                }
        ) {
            Text(
                text = if (selectedOption.isEmpty()) "Select Point Type" else selectedOption,
                modifier = Modifier.weight(1f),
                fontSize = 18.sp
            )
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .padding(3.dp)
                .background(Color.White)
                .width(with(LocalDensity.current) { buttonWidth.toDp() })
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option,
                            fontSize = 16.dp.value.sp,
                            color = Color(0xFF2C237B)
                        )
                    },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}


