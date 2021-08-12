package com.san4illa.weather.usecase

import com.san4illa.weather.repository.CityNameRepository
import com.san4illa.weather.repository.LocationRepository
import com.san4illa.weather.utils.UseCase
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class CityNameUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
    private val cityNameRepository: CityNameRepository
) : UseCase<Unit, String>(Dispatchers.IO) {
    override suspend fun execute(parameters: Unit): String {
        val location = locationRepository.getLocation()!!
        return cityNameRepository.getCityName(location)
    }
}