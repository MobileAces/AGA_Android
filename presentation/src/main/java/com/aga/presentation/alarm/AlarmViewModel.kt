package com.aga.presentation.alarm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aga.domain.model.Alarm
import com.aga.domain.model.AlarmDetail
import com.aga.domain.model.AlarmWithDetailList
import com.aga.domain.usecase.alarm.CreateAlarmUseCase
import com.aga.domain.usecase.alarm.GetAlarmWithDetailListByTeamIdUseCase
import com.aga.domain.usecase.alarm.ModifyAlarmDetailUseCase
import com.aga.presentation.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val createAlarmUseCase: CreateAlarmUseCase,
    private val getAlarmWithDetailListByTeamIdUseCase: GetAlarmWithDetailListByTeamIdUseCase,
    private val modifyAlarmDetailUseCase: ModifyAlarmDetailUseCase
) : ViewModel() {
    // 알람 <-> 알람 세팅 간 사용 데이터
    var selectedAlarm: Alarm? = null
    var selectedAlarmDetailId: Int? = null


    private var _alarmCreateResult = MutableLiveData<Event<Result<Alarm>>>()
    val alarmCreateResult: LiveData<Event<Result<Alarm>>>
        get() = _alarmCreateResult

    private var _alarmListResult = MutableLiveData<Result<List<AlarmWithDetailList>>>()
    val alarmListResult: LiveData<Result<List<AlarmWithDetailList>>>
        get() = _alarmListResult

    private var _alarmDetailModifyResult = MutableLiveData<Result<AlarmDetail>>()
    val alarmDetailModifyResult: LiveData<Result<AlarmDetail>>
        get() = _alarmDetailModifyResult


    fun createAlarm(alarmName: String, alarmDay: Set<DayOfWeek>, teamId: Int) {
        viewModelScope.launch {
            createAlarmUseCase(alarmName, alarmDay, teamId).let {
                _alarmCreateResult.postValue(Event(it))
            }
        }
    }

    fun getAlarmList(teamId: Int) {
        viewModelScope.launch {
            getAlarmWithDetailListByTeamIdUseCase(teamId).let {
                _alarmListResult.postValue(it)
            }
        }
    }

    fun modifyAlarmDetail(alarmDetail: AlarmDetail){
        viewModelScope.launch {
            modifyAlarmDetailUseCase(alarmDetail).let {
                _alarmDetailModifyResult.postValue(it)
            }
        }
    }


}