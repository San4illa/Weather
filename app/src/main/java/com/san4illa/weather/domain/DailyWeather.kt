package com.san4illa.weather.domain

data class DailyWeather(
    val time: Long,
    val minTemperature: String,
    val maxTemperature: String,
    val summary: String,
    val iconUrl: String
)