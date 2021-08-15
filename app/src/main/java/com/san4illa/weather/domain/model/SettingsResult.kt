package com.san4illa.weather.domain.model

sealed class SettingsResult {
    object Enabled : SettingsResult()
    class Disabled(val error: GpsSettingsException) : SettingsResult()
    class Unknown(val error: Throwable) : SettingsResult()
}