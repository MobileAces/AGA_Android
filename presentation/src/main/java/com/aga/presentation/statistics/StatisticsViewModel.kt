package com.aga.presentation.statistics

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aga.domain.model.DailyStatistics
import com.aga.domain.model.PeriodStatistics
import com.aga.domain.usecase.statistics.GetDailyStatisticsUseCase
import com.aga.domain.usecase.statistics.GetPeriodStatisticsUseCase
import com.aga.presentation.base.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "StatisticsViewModel_AWSOME"

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val getPeriodStatisticsUseCase: GetPeriodStatisticsUseCase,
    private val getDailyStatisticsUseCase: GetDailyStatisticsUseCase
): ViewModel(){
    private val _toastMsg = MutableLiveData<String>()
    val toastMsg: LiveData<String>
        get() = _toastMsg

    private val _periodStatisticsResult = MutableLiveData<PeriodStatistics>()
    val periodStatisticsResult: LiveData<PeriodStatistics>
        get() = _periodStatisticsResult

    private val _dailyStatisticsResult = MutableLiveData<List<DailyStatistics>>()
    val dailyStatisticsResult: LiveData<List<DailyStatistics>>
        get() = _dailyStatisticsResult

    fun getPeriodStatistics(userList: List<String>, teamId: Int, startDate: String, endDate: String){
        viewModelScope.launch {
            try {
                val response = getPeriodStatisticsUseCase.invoke(userList, teamId, startDate, endDate)
                if (response.totalSum == -1)
                    _toastMsg.value = LOAD_ERR
                else
                    _periodStatisticsResult.value = response
            }catch (e: Exception){
                Log.d(TAG, "getPeriodStatistics: ${e.message}")
                _toastMsg.value = Constants.NET_ERR
            }
        }
    }

    fun getDailyStatistics(teamId: Int, date: String){
        viewModelScope.launch {
            try {
                val response = getDailyStatisticsUseCase.invoke(teamId, date)
                Log.d(TAG, "getDailyStatistics: ${response.size}")
                if (response.isEmpty())
                    _toastMsg.value = LOAD_ERR
                else
                    _dailyStatisticsResult.value = response
            }catch (e: Exception){
                Log.d(TAG, "getDailyStatistics err: ${e.message}")
                _toastMsg.value = Constants.NET_ERR
            }
        }
    }
    companion object{
        const val LOAD_ERR = "정보를 불러오지 못했습니다."
    }
}