package com.san4illa.weather.data.repository.location

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class GmsLocationDataSource @Inject constructor(
    private val locationProvider: FusedLocationProviderClient
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
}