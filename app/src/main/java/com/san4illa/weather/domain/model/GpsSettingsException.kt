package com.san4illa.weather.domain.model

import android.app.PendingIntent

class GpsSettingsException(val resolution: PendingIntent) : Exception()