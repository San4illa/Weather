package com.san4illa.weather.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.san4illa.weather.domain.Weather
import com.san4illa.weather.network.WeatherApi
import com.san4illa.weather.network.toWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository {
    private val _currentlyWeather = MutableLiveData<Weather>()
    val currentlyWeather: LiveData<Weather>
        get() = _currentlyWeather

    suspend fun refreshWeather() {
        withContext(Dispatchers.IO) {
            val weatherResponse = WeatherApi.service.getWeather()

            withContext(Dispatchers.Main) {
                _currentlyWeather.value = weatherResponse.toWeather()
            }
        }
    }
}