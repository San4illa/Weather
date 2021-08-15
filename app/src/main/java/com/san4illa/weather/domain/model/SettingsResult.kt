package com.san4illa.weather.domain.model

sealed class SettingsResult {
    object Enabled : SettingsResult()
    class Disabled(val error: Throwable) : SettingsResult()
    class Unknown(val error: Throwable) : SettingsResult()
}