package com.aga.presentation.alarm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aga.domain.model.Alarm
import com.aga.domain.usecase.alarm.CreateAlarmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val createAlarmUseCase: CreateAlarmUseCase
): ViewModel() {
    private var _alarmCreateResult = MutableLiveData<Result<Alarm>>()
    val alarmCreateResult: LiveData<Result<Alarm>>
        get() = _alarmCreateResult



    fun createAlarm(alarmName: String, alarmDay: Set<DayOfWeek>, teamId: Int){
        viewModelScope.launch {
            createAlarmUseCase(alarmName, alarmDay, teamId).let {
                _alarmCreateResult.postValue(it)
            }
        }
    }
}