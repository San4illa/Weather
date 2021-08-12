package com.san4illa.weather.data.repository

import android.content.Context
import android.location.Geocoder
import android.location.Location
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

class CityNameRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getCityName(location: Location): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        return if (addresses.isNotEmpty()) addresses[0].locality else ""
    }
}