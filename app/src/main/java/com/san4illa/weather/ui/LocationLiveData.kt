package com.san4illa.weather.ui

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class LocationLiveData(context: Context) : LiveData<Location>() {
    private val locationProvider = LocationServices.getFusedLocationProviderClient(context)

    private val callback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return
            for (location in locationResult.locations) {
                value = location
            }
        }
    }

    private val request = LocationRequest.create().apply {
        numUpdates = 1
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    @SuppressLint("MissingPermission")
    override fun onActive() {
        super.onActive()
        locationProvider.lastLocation
            .addOnSuccessListener { location ->
                location?.let { value = location }
            }
        locationProvider.requestLocationUpdates(request, callback, null)
    }

    override fun onInactive() {
        super.onInactive()
        locationProvider.removeLocationUpdates(callback)
    }
}