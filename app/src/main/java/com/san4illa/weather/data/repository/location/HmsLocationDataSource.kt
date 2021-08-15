package com.san4illa.weather.data.repository.location

import android.location.Location
import com.huawei.hms.location.FusedLocationProviderClient
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class HmsLocationDataSource @Inject constructor(
    private val locationProvider: FusedLocationProviderClient
) {
    suspend fun getLocation(): Location? {
        return suspendCoroutine { continuation ->
            locationProvider.lastLocation
                .addOnSuccessListener { location ->
                    continuation.resume(location)
                }
        }
    }
}