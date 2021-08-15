package com.san4illa.weather.data.repository.location

import android.location.Location
import com.huawei.hms.common.ResolvableApiException
import com.huawei.hms.location.FusedLocationProviderClient
import com.huawei.hms.location.LocationRequest
import com.huawei.hms.location.LocationSettingsRequest
import com.huawei.hms.location.SettingsClient
import com.san4illa.weather.domain.model.SettingsException
import com.san4illa.weather.domain.model.SettingsResult
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class HmsLocationDataSource @Inject constructor(
    private val locationProvider: FusedLocationProviderClient,
    private val locationSettings: SettingsClient
) {
    suspend fun getLocation(): Location? {
        return suspendCoroutine { continuation ->
            locationProvider.lastLocation
                .addOnSuccessListener { location ->
                    continuation.resume(location)
                }
        }
    }

    suspend fun checkLocationSettings(): SettingsResult {
        return suspendCoroutine { continuation ->
            val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(getLocationRequest())
                .build()
            locationSettings.checkLocationSettings(builder)
                .addOnSuccessListener {
                    continuation.resume(SettingsResult.Enabled)
                }
                .addOnFailureListener { exception ->
                    if (exception is ResolvableApiException) {
                        val settingsException = SettingsException(exception.resolution)
                        continuation.resume(SettingsResult.Disabled(settingsException))
                    } else {
                        continuation.resume(SettingsResult.Unknown(exception))
                    }
                }
        }
    }

    private fun getLocationRequest() = LocationRequest.create().apply {
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        interval = 10000
    }
}