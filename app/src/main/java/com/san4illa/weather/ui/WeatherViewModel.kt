package com.san4illa.weather.ui

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san4illa.weather.repository.Repository
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val repository = Repository()

    val currentlyWeather = repository.currentlyWeather

    fun onLocationUpdated(location: Location) {
        refreshWeather(location)
    }

    private fun refreshWeather(location: Location) {
        viewModelScope.launch {
            try {
                repository.refreshWeather(location.latitude, location.longitude)
            } catch (error: Exception) {
                Log.i("WeatherViewModel", "error ${error.message}")
            }
        }
    }
}