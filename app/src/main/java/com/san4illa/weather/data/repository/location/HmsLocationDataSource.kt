package com.san4illa.weather.data.repository.location

import android.content.Context
import android.location.Location
import com.huawei.hms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class HmsLocationDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val locationProvider = LocationServices.getFusedLocationProviderClient(context)

    suspend fun getLocation(): Location? {
        return suspendCoroutine { continuation ->
            locationProvider.lastLocation
                .addOnSuccessListener { location ->
                    continuation.resume(location)
                }
        }
    }
}