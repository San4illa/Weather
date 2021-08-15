package com.san4illa.weather.domain.usecase

import com.san4illa.weather.domain.model.WeatherForecast
import com.san4illa.weather.data.repository.location.LocationRepository
import com.san4illa.weather.data.repository.WeatherRepository
import com.san4illa.weather.domain.UseCase
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class WeatherUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository
) : UseCase<Unit, WeatherForecast>(Dispatchers.IO) {
    override suspend fun execute(parameters: Unit): WeatherForecast {
        val location = locationRepository.getLocation()!!
        return weatherRepository.getForecast(location.latitude, location.longitude)
    }
}