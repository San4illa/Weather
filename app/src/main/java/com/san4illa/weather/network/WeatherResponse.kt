package com.san4illa.weather.network

data class WeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val currently: WeatherItem,
    val hourly: WeatherWrapper,
    val daily: WeatherWrapper
)

data class WeatherItem(
    val time: Long,
    val temperature: Double,
    val windSpeed: Double,
    val humidity: Double,
    val pressure: Double,
    val summary: String,
    val icon: String
)

data class WeatherWrapper(
    val summary: String,
    val icon: String,
    val data: List<WeatherItem>
)