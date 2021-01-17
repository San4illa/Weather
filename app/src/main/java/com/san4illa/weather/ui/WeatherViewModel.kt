package com.san4illa.weather.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san4illa.weather.repository.Repository
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val repository = Repository()

    val currentlyWeather = repository.currentlyWeather

    init {
        refreshWeather()
    }

    private fun refreshWeather() {
        viewModelScope.launch {
            try {
                repository.refreshWeather()
            } catch (error: Exception) {
                Log.i("WeatherViewModel", "error ${error.message}")
            }
        }
    }
}