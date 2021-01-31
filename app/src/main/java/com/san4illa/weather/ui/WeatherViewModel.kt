package com.san4illa.weather.ui

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san4illa.weather.network.State
import com.san4illa.weather.repository.Repository
import kotlinx.coroutines.launch

class WeatherViewModel(context: Context) : ViewModel() {
    val location = LocationLiveData(context)

    private val repository = Repository()
    val currentlyWeather = repository.currentlyWeather
    val hourlyWeather = repository.hourlyWeather
    val dailyWeather = repository.dailyWeather

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    init {
        _state.value = State.LOADING
    }

    fun onLocationUpdated(location: Location) {
        refreshWeather(location)
    }

    private fun refreshWeather(location: Location) {
        viewModelScope.launch {
            try {
                repository.refreshWeather(location.latitude, location.longitude)
                _state.value = State.DONE
            } catch (error: Exception) {
                Log.i("WeatherViewModel", "error ${error.message}")
                _state.value = State.ERROR
            }
        }
    }
}