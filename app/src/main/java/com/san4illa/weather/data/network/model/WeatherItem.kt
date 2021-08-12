package com.san4illa.weather.data.network.model

data class WeatherItem(
    val time: Long,
    val temperature: Double?,
    val temperatureHigh: Double?,
    val temperatureLow: Double?,
    val windSpeed: Double,
    val humidity: Double,
    val pressure: Double,
    val summary: String,
    val icon: String
)