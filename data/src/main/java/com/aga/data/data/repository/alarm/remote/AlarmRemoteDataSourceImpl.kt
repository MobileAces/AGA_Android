package com.aga.data.data.repository.alarm.remote

import com.aga.data.data.api.AlarmService
import com.aga.data.data.model.alarm.AlarmDataRequest
import com.aga.data.data.model.alarm.AlarmDataResponse
import com.aga.data.data.model.alarm.AlarmWithDetailListResponse
import javax.inject.Inject

class AlarmRemoteDataSourceImpl @Inject constructor(
    private val alarmService: AlarmService
) : AlarmRemoteDataSource {
    override suspend fun createNewAlarm(alarmDataRequest: AlarmDataRequest): Result<AlarmDataResponse> {
        return alarmService.createNewAlarm(alarmDataRequest).let {
            if (it.isSuccessful && it.body() != null) {
                Result.success(it.body()!!.data!!)
            } else {
                Result.failure(Exception(it.code().toString()))
            }
        }
    }

    override suspend fun getAlarmListByTeamId(teamId: Int): Result<List<AlarmWithDetailListResponse>> {
        return alarmService.getAlarmListByTeamId(teamId).let {
            if (it.isSuccessful && it.body() != null){
                Result.success(it.body()!!.dataList!!)
            } else {
                Result.failure(Exception(it.code().toString()))
            }
        }
    }

    override suspend fun deleteAlarmById(id: Int): Result<Boolean> {
        return alarmService.deleteAlarmById(id).let {
            if (it.isSuccessful && it.body() != null){
                Result.success(it.body()!!.data!!.result)
            } else {
                Result.failure(Exception(it.code().toString()))
            }
        }
    }
}