package com.san4illa.weather.ui

import android.content.Context
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san4illa.weather.domain.WeatherForecast
import com.san4illa.weather.network.State
import com.san4illa.weather.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: Repository
) : ViewModel() {
    val location = LocationLiveData(context)

    private val _city = MutableLiveData<String>()
    val city: LiveData<String>
        get() = _city

    private val _weatherForecast = MutableLiveData<WeatherForecast>()
    val weatherForecast: LiveData<WeatherForecast>
        get() = _weatherForecast

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    init {
        _state.value = State.LOADING
    }

    fun onLocationUpdated(location: Location) {
        getForecast(location)
    }

    private fun getForecast(location: Location) {
        viewModelScope.launch {
            try {
                val weatherForecast = repository.getForecast(location.latitude, location.longitude)
                _weatherForecast.value = weatherForecast

                getCityName(location)
                _state.value = State.DONE
            } catch (error: Exception) {
                _state.value = State.ERROR
            }
        }
    }

    private fun getCityName(location: Location) {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        if (addresses.isNotEmpty()) {
            _city.value = addresses[0].locality
        }
    }
}