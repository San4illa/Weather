package com.san4illa.weather.data.network

import com.san4illa.weather.data.network.dto.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {
    @GET("{latitude},{longitude}")
    suspend fun getWeather(
        @Path("latitude") latitude: Double,
        @Path("longitude") longitude: Double,
        @Query("units") units: String = "si",
        @Query("lang") lang: String = "ru"
    ): WeatherDto
}