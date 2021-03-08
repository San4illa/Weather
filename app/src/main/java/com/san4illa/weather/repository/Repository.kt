package com.san4illa.weather.repository

import com.san4illa.weather.domain.WeatherForecast
import com.san4illa.weather.network.WeatherApi
import com.san4illa.weather.network.model.toForecast

class Repository {
    suspend fun getForecast(latitude: Double, longitude: Double): WeatherForecast {
        return WeatherApi.service.getWeather(latitude, longitude)
            .toForecast()
    }
}