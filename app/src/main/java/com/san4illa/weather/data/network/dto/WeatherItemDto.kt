package com.san4illa.weather.data.network.dto

import com.google.gson.annotations.SerializedName
import com.san4illa.weather.domain.model.DailyWeather
import com.san4illa.weather.domain.model.Weather
import kotlin.math.roundToInt

data class WeatherItemDto(
    @SerializedName("time") val time: Long,
    @SerializedName("temperature") val temperature: Double = 0.0,
    @SerializedName("temperatureHigh") val temperatureHigh: Double = 0.0,
    @SerializedName("temperatureLow") val temperatureLow: Double = 0.0,
    @SerializedName("windSpeed") val windSpeed: Double,
    @SerializedName("humidity") val humidity: Double,
    @SerializedName("pressure") val pressure: Double,
    @SerializedName("summary") val summary: String,
    @SerializedName("icon") val icon: String
) {
    fun toWeather() = Weather(
        time = time,
        temperature = format(temperature),
        summary = summary,
        iconUrl = createIconUrl(icon)
    )

    private fun format(temperature: Double): String {
        return "${temperature.roundToInt()}°"
    }

    private fun createIconUrl(icon: String): String {
        return "https://darksky.net/images/weather-icons/${icon}.png"
    }

    fun toDailyWeather() = DailyWeather(
        time = time,
        minTemperature = format(temperatureLow),
        maxTemperature = format(temperatureHigh),
        summary = summary,
        iconUrl = createIconUrl(icon)
    )
}