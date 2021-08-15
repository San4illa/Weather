package com.san4illa.weather.data.repository.location

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.SettingsClient
import com.san4illa.weather.domain.model.SettingsResult
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class GmsLocationDataSource @Inject constructor(
    private val locationProvider: FusedLocationProviderClient,
    private val locationSettings: SettingsClient
) {
    @SuppressLint("MissingPermission")
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
                        continuation.resume(SettingsResult.Disabled(exception))
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