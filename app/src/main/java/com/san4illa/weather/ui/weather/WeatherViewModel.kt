package com.san4illa.weather.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.api.ResolvableApiException
import com.san4illa.weather.domain.model.SettingsResult.*
import com.san4illa.weather.domain.model.State
import com.san4illa.weather.domain.model.WeatherForecast
import com.san4illa.weather.domain.usecase.CheckSettingsUseCase
import com.san4illa.weather.domain.usecase.CityNameUseCase
import com.san4illa.weather.domain.usecase.WeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val checkSettingsUseCase: CheckSettingsUseCase,
    private val weatherUseCase: WeatherUseCase,
    private val cityNameUseCase: CityNameUseCase
) : ViewModel() {
    private val _city = MutableLiveData<String>()
    val city: LiveData<String>
        get() = _city

    private val _weatherForecast = MutableLiveData<WeatherForecast>()
    val weatherForecast: LiveData<WeatherForecast>
        get() = _weatherForecast

    private val _state = MutableLiveData(State.LOADING)
    val state: LiveData<State>
        get() = _state

    private val _requestLocationSettings = MutableLiveData<ResolvableApiException>()
    val requestLocationSettings: LiveData<ResolvableApiException>
        get() = _requestLocationSettings

    private val _closeActivity = MutableLiveData(false)
    val closeActivity: LiveData<Boolean>
        get() = _closeActivity

    fun onPermissionsResult(areGranted: Boolean) {
        if (areGranted) onPermissionsGranted() else onPermissionsDenied()
    }

    private fun onPermissionsGranted() {
        viewModelScope.launch {
            checkSettingsUseCase.invoke(Unit).handle({ settings ->
                when (settings) {
                    is Enabled -> getData()
                    is Disabled -> requestGps(settings.error)
                    is Unknown -> showError()
                }
            })
        }
    }

    private fun getData() {
        viewModelScope.launch {
            weatherUseCase.invoke(Unit).handle({ weatherForecast ->
                _weatherForecast.value = weatherForecast
                _state.value = State.DONE
            }, {
                showError()
            })

            cityNameUseCase.invoke(Unit).handle({ cityName ->
                _city.value = cityName
            })
        }
    }

    private fun requestGps(error: Throwable) {
        _requestLocationSettings.value = error as ResolvableApiException
    }

    private fun showError() {
        _state.value = State.ERROR
    }

    private fun onPermissionsDenied() {
        closeActivity()
    }

    private fun closeActivity() {
        _closeActivity.value = true
    }

    fun onSettingsResult(isGpsEnabled: Boolean) {
        if (isGpsEnabled) getData() else closeActivity()
    }
}