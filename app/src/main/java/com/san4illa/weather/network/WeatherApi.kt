package com.san4illa.weather.network

import com.san4illa.weather.BuildConfig
import com.san4illa.weather.network.model.WeatherResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://api.darksky.net/forecast/${BuildConfig.API_KEY}/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface WeatherService {
    @GET("{latitude},{longitude}?units=si&lang=ru")
    suspend fun getWeather(
        @Path("latitude") latitude: Double = 37.8267,
        @Path("longitude") longitude: Double = -122.4233
    ): WeatherResponse
}

object WeatherApi {
    val service: WeatherService by lazy {
        retrofit.create(WeatherService::class.java)
    }
}

enum class State {
    LOADING, DONE, ERROR
}