package com.aga.data.data.repository.alarm.remote

import com.aga.data.data.api.AlarmService
import com.aga.data.data.model.alarm.AlarmDataRequest
import com.aga.data.data.model.alarm.AlarmDataResponse
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
}