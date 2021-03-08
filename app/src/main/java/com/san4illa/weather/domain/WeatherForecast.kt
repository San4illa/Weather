package com.san4illa.weather.domain

data class WeatherForecast(
    val currentlyWeather: Weather,
    val hourlyWeather: List<Weather>,
    val dailyWeather: List<DailyWeather>
)