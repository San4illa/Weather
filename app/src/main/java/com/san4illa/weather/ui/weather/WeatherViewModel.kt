package com.san4illa.weather.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san4illa.weather.domain.model.State
import com.san4illa.weather.domain.model.WeatherForecast
import com.san4illa.weather.domain.usecase.CityNameUseCase
import com.san4illa.weather.domain.usecase.WeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
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

    private val _closeActivity = MutableLiveData(false)
    val closeActivity: LiveData<Boolean>
        get() = _closeActivity

    fun onPermissionsResult(areGranted: Boolean) {
        if (areGranted) onPermissionsGranted() else onPermissionsDenied()
    }

    private fun onPermissionsGranted() {
        viewModelScope.launch {
            weatherUseCase.invoke(Unit).handle({ weatherForecast ->
                _weatherForecast.value = weatherForecast
                _state.value = State.DONE
            }, {
                _state.value = State.ERROR
            })

            cityNameUseCase.invoke(Unit).handle({ cityName ->
                _city.value = cityName
            })
        }
    }

    private fun onPermissionsDenied() {
        _closeActivity.value = true
    }
}