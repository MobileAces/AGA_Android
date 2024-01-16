package com.aga.presentation.alarm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aga.domain.model.WakeUp
import com.aga.domain.model.Weather
import com.aga.domain.usecase.statistics.RegisterWakeUpUseCase
import com.aga.domain.usecase.weather.GetWeatherInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "AlarmRingViewModel_AWSOME"
@HiltViewModel
class AlarmRingViewModel @Inject constructor(
    private val getWeatherInfoUseCase: GetWeatherInfoUseCase,
    private val registerWakeUpUseCase: RegisterWakeUpUseCase
): ViewModel() {
    private val _weatherResult = MutableLiveData<Weather>()
    val weatherResult: LiveData<Weather>
        get() = _weatherResult

    private val _registerWakeUpResult = MutableLiveData<Boolean>()
    val registerWakeUpResult: LiveData<Boolean>
        get() = _registerWakeUpResult

    fun getWeatherInfo(lat: Double, long: Double){
        viewModelScope.launch {
            try {
                _weatherResult.value =  getWeatherInfoUseCase.invoke(lat, long)
            }catch (e: Exception){
                Log.d(TAG, "getWeatherInfo: ${e.message}")
                _weatherResult.value = Weather(-1.0, "FAIL")
            }
        }
    }

    fun registerWakeUp(wakeUp: WakeUp){
        viewModelScope.launch {
            try {
                _registerWakeUpResult.value = registerWakeUpUseCase.invoke(wakeUp)
            }catch (e: Exception){
                _registerWakeUpResult.value = false
            }

        }
    }

}