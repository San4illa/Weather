package com.san4illa.weather.data.network.model

import com.san4illa.weather.domain.model.DailyWeather
import com.san4illa.weather.domain.model.Weather
import com.san4illa.weather.domain.model.WeatherForecast
import kotlin.math.round

data class WeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val currently: WeatherItem,
    val hourly: WeatherWrapper,
    val daily: WeatherWrapper
)

fun WeatherResponse.toForecast(): WeatherForecast {
    return WeatherForecast(
        toCurrentWeather(),
        toHourlyWeather(),
        toDailyWeather()
    )
}

private fun WeatherResponse.toCurrentWeather(): Weather {
    return currently.toWeather()
}

private fun WeatherItem.toWeather() = Weather(
    time = time,
    temperature = "${round(temperature!!).toInt()}°",
    summary = summary,
    iconUrl = "https://darksky.net/images/weather-icons/${icon}.png"
)

private fun WeatherResponse.toHourlyWeather(): List<Weather> {
    return hourly.data.map { it.toWeather() }
}

private fun WeatherResponse.toDailyWeather(): List<DailyWeather> {
    return daily.data.map { it.toDailyWeather() }
}

private fun WeatherItem.toDailyWeather() = DailyWeather(
    time = time,
    minTemperature = "${round(temperatureLow!!).toInt()}°",
    maxTemperature = "${round(temperatureHigh!!).toInt()}°",
    summary = summary,
    iconUrl = "https://darksky.net/images/weather-icons/${icon}.png"
)