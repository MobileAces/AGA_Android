package com.aga.presentation.statistics

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aga.domain.model.PeriodStatistics
import com.aga.domain.usecase.statistics.GetPeriodStatisticsUseCase
import com.aga.presentation.base.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "StatisticsViewModel_AWSOME"

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val getPeriodStatisticsUseCase: GetPeriodStatisticsUseCase
): ViewModel(){
    private val _toastMsg = MutableLiveData<String>()
    val toastMsg: LiveData<String>
        get() = _toastMsg

    private val _periodStatisticsResult = MutableLiveData<PeriodStatistics>()
    val periodStatisticsResult: LiveData<PeriodStatistics>
        get() = _periodStatisticsResult

    fun getPeriodStatistics(userList: List<String>, teamId: Int, startDate: String, endDate: String){
        viewModelScope.launch {
            try {
                getPeriodStatisticsUseCase.invoke(userList, teamId, startDate, endDate)
            }catch (e: Exception){
                Log.d(TAG, "getPeriodStatistics: ${e.message}")
                _toastMsg.value = Constants.NET_ERR
            }
        }
    }
}