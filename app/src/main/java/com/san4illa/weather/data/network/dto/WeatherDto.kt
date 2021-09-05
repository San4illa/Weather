package com.san4illa.weather.data.network.dto

import com.google.gson.annotations.SerializedName
import com.san4illa.weather.domain.model.DailyWeather
import com.san4illa.weather.domain.model.Weather
import com.san4illa.weather.domain.model.WeatherForecast

data class WeatherDto(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("currently") val currently: WeatherItemDto,
    @SerializedName("hourly") val hourly: WeatherWrapperDto,
    @SerializedName("daily") val daily: WeatherWrapperDto
) {
    fun toDomain(): WeatherForecast {
        return WeatherForecast(
            getCurrentWeather(),
            getHourlyWeather(),
            getDailyWeather()
        )
    }

    private fun getCurrentWeather(): Weather {
        return currently.toWeather()
    }

    private fun getHourlyWeather(): List<Weather> {
        return hourly.data.map { it.toWeather() }
    }

    private fun getDailyWeather(): List<DailyWeather> {
        return daily.data.map { it.toDailyWeather() }
    }
}