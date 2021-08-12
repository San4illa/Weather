package com.san4illa.weather.data.network.model

data class WeatherWrapper(
    val summary: String,
    val icon: String,
    val data: List<WeatherItem>
)