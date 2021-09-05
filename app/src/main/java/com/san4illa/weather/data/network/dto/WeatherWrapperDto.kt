package com.san4illa.weather.data.network.dto

import com.google.gson.annotations.SerializedName

data class WeatherWrapperDto(
    @SerializedName("summary") val summary: String,
    @SerializedName("icon") val icon: String,
    @SerializedName("data") val data: List<WeatherItemDto>
)