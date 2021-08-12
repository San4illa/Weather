package com.san4illa.weather.usecase

import com.san4illa.weather.domain.WeatherForecast
import com.san4illa.weather.repository.LocationRepository
import com.san4illa.weather.repository.WeatherRepository
import com.san4illa.weather.utils.UseCase
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