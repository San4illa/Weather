package com.san4illa.weather.domain.model

import android.app.PendingIntent

class SettingsException(val resolution: PendingIntent) : Exception()