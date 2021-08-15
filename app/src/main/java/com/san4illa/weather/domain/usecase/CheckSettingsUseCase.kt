package com.san4illa.weather.domain.usecase

import com.san4illa.weather.data.repository.location.LocationRepository
import com.san4illa.weather.domain.UseCase
import com.san4illa.weather.domain.model.SettingsResult
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class CheckSettingsUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) : UseCase<Unit, SettingsResult>(Dispatchers.IO) {
    override suspend fun execute(parameters: Unit): SettingsResult {
        return locationRepository.checkLocationSettings()
    }
}