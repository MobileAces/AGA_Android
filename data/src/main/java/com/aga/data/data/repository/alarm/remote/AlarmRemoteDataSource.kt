package com.aga.data.data.repository.alarm.remote

import com.aga.data.data.model.alarm.AlarmDataRequest
import com.aga.data.data.model.alarm.AlarmDataResponse

interface AlarmRemoteDataSource {
    suspend fun createNewAlarm(alarmDataRequest: AlarmDataRequest): Result<AlarmDataResponse>
}