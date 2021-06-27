package com.san4illa.weather.repository

import com.san4illa.weather.domain.WeatherForecast
import com.san4illa.weather.network.WeatherService
import com.san4illa.weather.network.model.toForecast
import javax.inject.Inject

class Repository @Inject constructor(
    private val weatherApi: WeatherService
) {
    suspend fun getForecast(latitude: Double, longitude: Double): WeatherForecast {
        return weatherApi.getWeather(latitude, longitude)
            .toForecast()
    }
}