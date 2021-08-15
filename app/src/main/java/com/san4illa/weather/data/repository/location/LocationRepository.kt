package com.san4illa.weather.data.repository.location

import android.location.Location
import com.san4illa.weather.data.repository.MobileServicesRepository
import com.san4illa.weather.domain.model.MobileServicesType.*
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val mobileServicesRepository: MobileServicesRepository,
    private val gmsLocationDataSource: GmsLocationDataSource,
    private val hmsLocationDataSource: HmsLocationDataSource
) {
    suspend fun getLocation(): Location? {
        return when (mobileServicesRepository.getMobileServicesType()) {
            GOOGLE -> gmsLocationDataSource.getLocation()
            HUAWEI -> hmsLocationDataSource.getLocation()
            NONE -> null
        }
    }
}