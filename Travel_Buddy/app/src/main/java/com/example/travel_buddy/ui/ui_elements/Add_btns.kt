package com.example.travel_buddy.ui.ui_elements

import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.travel_buddy.R

@Composable
fun Add_btns() {
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
    if (isDialogOpen) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Mood Radio", style = MaterialTheme.typography.titleLarge) },
            text = { Text(text) },
            confirmButton = {
                Button(onClick = onDismiss) {
                    Text("OK")
                }
            },
            modifier = Modifier.padding(16.dp),
            shape = MaterialTheme.shapes.medium
        )
    }
}
