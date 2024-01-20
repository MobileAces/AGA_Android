package com.aga.data.data.repository.alarm.remote

import com.aga.data.data.model.DefaultBooleanData
import com.aga.data.data.model.DefaultListResponse
import com.aga.data.data.model.DefaultResponse
import com.aga.data.data.model.alarm.AlarmDataRequest
import com.aga.data.data.model.alarm.AlarmDataResponse
import com.aga.data.data.model.alarm.AlarmWithDetailListResponse
import retrofit2.Response

interface AlarmRemoteDataSource {
    suspend fun createNewAlarm(alarmDataRequest: AlarmDataRequest): Result<AlarmDataResponse>

    suspend fun getAlarmListByTeamId(teamId: Int): Result<List<AlarmWithDetailListResponse>>

    suspend fun deleteAlarmById(id: Int): Result<Boolean>
}