package com.san4illa.weather.data.repository.location

import android.location.Location
import com.san4illa.weather.domain.model.SettingsResult

interface LocationRepository {
    suspend fun getLocation(): Location?
    suspend fun checkLocationSettings(): SettingsResult
}