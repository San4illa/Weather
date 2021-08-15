package com.san4illa.weather.data.repository

import android.content.Context
import com.google.android.gms.common.GoogleApiAvailability
import com.huawei.hms.api.HuaweiApiAvailability
import com.san4illa.weather.domain.model.MobileServicesType
import com.san4illa.weather.domain.model.MobileServicesType.*
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MobileServicesRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getMobileServicesType(): MobileServicesType {
        return when {
            isGoogleServicesAvailable() -> GOOGLE
            isHuaweiServicesAvailable() -> HUAWEI
            else -> NONE
        }
    }

    private fun isGoogleServicesAvailable(): Boolean {
        val googleApi = GoogleApiAvailability.getInstance()
        return googleApi.isGooglePlayServicesAvailable(context) == com.google.android.gms.common.ConnectionResult.SUCCESS
    }

    private fun isHuaweiServicesAvailable(): Boolean {
        val huaweiApi = HuaweiApiAvailability.getInstance()
        return huaweiApi.isHuaweiMobileServicesAvailable(context) == com.huawei.hms.api.ConnectionResult.SUCCESS
    }
}