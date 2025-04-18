package com.example.travel_buddy.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class TempDataViewModel : ViewModel() {
    var text by mutableStateOf("Travel Buddy")
    var lastTravel by mutableStateOf("")
    fun updateText(newText: String) {
        text = newText
        Log.d("text_update", "updatedText:  $text")
    }
}