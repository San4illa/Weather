package com.san4illa.weather.network

import com.san4illa.weather.domain.DailyWeather
import com.san4illa.weather.domain.Weather
import kotlin.math.round

data class WeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val currently: WeatherItem,
    val hourly: WeatherWrapper,
    val daily: WeatherWrapper
)

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

data class WeatherWrapper(
    val summary: String,
    val icon: String,
    val data: List<WeatherItem>
)

fun WeatherResponse.toCurrentWeather(): Weather {
    return currently.toWeather()
}

private fun WeatherItem.toWeather() = Weather(
    time = time,
    temperature = "${round(temperature!!).toInt()}°",
    summary = summary,
    iconUrl = "https://darksky.net/images/weather-icons/${icon}.png"
)

fun WeatherResponse.toHourlyWeather(): List<Weather> {
    return hourly.data.map { it.toWeather() }
}

fun WeatherResponse.toDailyWeather(): List<DailyWeather> {
    return daily.data.map { it.toDailyWeather() }
}

private fun WeatherItem.toDailyWeather() = DailyWeather(
    time = time,
    minTemperature = "${round(temperatureLow!!).toInt()}°",
    maxTemperature = "${round(temperatureHigh!!).toInt()}°",
    summary = summary,
    iconUrl = "https://darksky.net/images/weather-icons/${icon}.png"
)