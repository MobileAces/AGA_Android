package com.aga.presentation.alarm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aga.domain.model.AlarmDetail
import com.aga.domain.usecase.alarm.CreateNewAlarmDetailUsecase
import com.aga.domain.usecase.alarm.GetSavedAlarmDetailById
import com.aga.domain.usecase.alarm.ModifyAlarmDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmSettingViewModel @Inject constructor(
    private val createNewAlarmDetailUsecase: CreateNewAlarmDetailUsecase,
    private val modifyAlarmDetailUseCase: ModifyAlarmDetailUseCase,
    private val getSavedAlarmDetailById: GetSavedAlarmDetailById
): ViewModel() {

    private var _alarmDetailCreateModifyResult = MutableLiveData<Result<AlarmDetail>>()
    val alarmDetailCreateModifyResult: LiveData<Result<AlarmDetail>>
        get() = _alarmDetailCreateModifyResult

    private var _savedAlarmDetail = MutableLiveData<AlarmDetail?>()
    val savedAlarmDetail: LiveData<AlarmDetail?>
        get() = _savedAlarmDetail

    fun createNewAlarm(alarmDetail: AlarmDetail){
        viewModelScope.launch {
            createNewAlarmDetailUsecase(alarmDetail).let {
                _alarmDetailCreateModifyResult.postValue(it)
            }
        }
    }

    fun modifyAlarmDetail(alarmDetail: AlarmDetail){
        viewModelScope.launch {
            modifyAlarmDetailUseCase(alarmDetail).let {
                _alarmDetailCreateModifyResult.postValue(it)
            }
        }
    }

    fun getAlarmDetailById(id: Int){
        viewModelScope.launch {
            getSavedAlarmDetailById(id).let {
                _savedAlarmDetail.postValue(it)
            }
        }
    }
}