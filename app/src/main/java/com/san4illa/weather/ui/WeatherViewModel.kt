package com.san4illa.weather.ui

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san4illa.weather.domain.WeatherForecast
import com.san4illa.weather.network.State
import com.san4illa.weather.repository.CityNameRepository
import com.san4illa.weather.repository.LocationRepository
import com.san4illa.weather.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: Repository,
    private val locationRepository: LocationRepository,
    private val cityNameRepository: CityNameRepository
) : ViewModel() {
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

    fun onPermissionsGranted() {
        viewModelScope.launch {
            val location = locationRepository.getLocation()
            location?.let {
                getForecast(location)
            }
        }
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
        _city.value = cityNameRepository.getCityName(location)
    }
}