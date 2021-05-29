package com.san4illa.weather.repository

import com.san4illa.weather.domain.WeatherForecast
import com.san4illa.weather.network.WeatherApi
import com.san4illa.weather.network.model.toForecast
import javax.inject.Inject

class Repository @Inject constructor() {
    suspend fun getForecast(latitude: Double, longitude: Double): WeatherForecast {
        return WeatherApi.service.getWeather(latitude, longitude)
            .toForecast()
    }
}