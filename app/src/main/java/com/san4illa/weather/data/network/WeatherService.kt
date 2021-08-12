package com.san4illa.weather.data.network

import com.san4illa.weather.data.network.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {
    @GET("{latitude},{longitude}?units=si&lang=ru")
    suspend fun getWeather(
        @Path("latitude") latitude: Double,
        @Path("longitude") longitude: Double
    ): WeatherResponse
}