package com.san4illa.weather.data.repository

import com.san4illa.weather.domain.model.WeatherForecast
import com.san4illa.weather.data.network.WeatherService
import com.san4illa.weather.data.network.model.toForecast
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherApi: WeatherService
) {
    suspend fun getForecast(latitude: Double, longitude: Double): WeatherForecast {
        return weatherApi.getWeather(latitude, longitude)
            .toForecast()
    }
}