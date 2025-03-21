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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.travel_buddy.classes_res.Travel_point


import com.example.travel_buddy.classes_res.heritage_points.Attraction_point
import com.example.travel_buddy.classes_res.heritage_points.Hotel_point
import com.example.travel_buddy.classes_res.heritage_points.Trip_point

import com.example.travel_buddy.functions.exportToPdf

import com.example.travel_buddy.ui.theme.Travel_BuddyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Travel_BuddyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Travel buddy",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    val test_travel = Travel_point("travel class")
    val test_attraction = Attraction_point("attraction class")
    val test_hotel = Hotel_point("hotel class")
    val test_trip = Trip_point("trip class")

    val test = Travel_point()
    val test2 = Travel_point()
    val test3 = Travel_point()
    val travel_points = arrayOf(test,test2,test3)
    val context = LocalContext.current
    exportToPdf(context,"testpdf",travel_points)

    Travel_Point_Manager.add_Point("Vacation", Hotel_point(name = "Hotel_1"))
    Travel_Point_Manager.add_Point("Vacation", Travel_point(name = "Mountain"))
    Travel_Point_Manager.add_Point("Business", Attraction_point(name = "Conference"))
    Column {
        Text(
            text = "Hello $name! \n $test_travel \n $test_attraction \n $test_hotel \n $test_trip",
            modifier = modifier
        )
        Text(
            text = "$Travel_Point_Manager",
            modifier = modifier
        )
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Travel_BuddyTheme {
        Greeting("Android")
    }
}