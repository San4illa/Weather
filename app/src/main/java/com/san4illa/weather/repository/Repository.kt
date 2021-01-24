package com.san4illa.weather.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.san4illa.weather.domain.Weather
import com.san4illa.weather.network.WeatherApi
import com.san4illa.weather.network.toCurrentWeather
import com.san4illa.weather.network.toHourlyWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository {
    private val _currentlyWeather = MutableLiveData<Weather>()
    val currentlyWeather: LiveData<Weather>
        get() = _currentlyWeather

    private val _hourlyWeather = MutableLiveData<List<Weather>>()
    val hourlyWeather: LiveData<List<Weather>>
        get() = _hourlyWeather

    suspend fun refreshWeather(latitude: Double, longitude: Double) {
        withContext(Dispatchers.IO) {
            val weatherResponse = WeatherApi.service.getWeather(latitude, longitude)

            withContext(Dispatchers.Main) {
                _currentlyWeather.value = weatherResponse.toCurrentWeather()
                _hourlyWeather.value = weatherResponse.toHourlyWeather()
            }
        }
    }
}