package com.san4illa.weather.domain.model

data class DailyWeather(
    val time: Long,
    val minTemperature: String,
    val maxTemperature: String,
    val summary: String,
    val iconUrl: String
)