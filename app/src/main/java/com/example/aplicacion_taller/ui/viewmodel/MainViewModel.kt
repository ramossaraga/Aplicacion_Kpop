package com.example.aplicacion_taller.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var selectedTab by mutableIntStateOf(0)
        private set

    fun onTabSelected(index: Int) {
        selectedTab = index
    }
}