package com.san4illa.weather.domain.model

data class Weather(
    val time: Long,
    val temperature: String,
    val summary: String,
    val iconUrl: String
)