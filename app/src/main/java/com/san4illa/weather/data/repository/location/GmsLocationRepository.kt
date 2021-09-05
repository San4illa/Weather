package com.san4illa.weather.data.repository.location

import android.annotation.SuppressLint
import android.location.Location
import android.os.Looper
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.san4illa.weather.domain.model.GpsSettingsException
import com.san4illa.weather.domain.model.SettingsResult
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class GmsLocationRepository(
    private val locationProvider: FusedLocationProviderClient,
    private val locationSettings: SettingsClient
) : LocationRepository {
    @SuppressLint("MissingPermission")
    override suspend fun getLocation(): Location? {
        return suspendCoroutine { continuation ->
            locationProvider.requestLocationUpdates(getLocationRequest(), object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    continuation.resume(locationResult?.lastLocation)
                    locationProvider.removeLocationUpdates(this)
                }
            }, Looper.getMainLooper())
        }
    }

    override suspend fun checkLocationSettings(): SettingsResult {
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
                        val settingsException = GpsSettingsException(exception.resolution)
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