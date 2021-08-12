package com.san4illa.weather.domain.model

data class WeatherForecast(
    val currentlyWeather: Weather,
    val hourlyWeather: List<Weather>,
    val dailyWeather: List<DailyWeather>
)