package com.aga.data.data.repository.alarmdetail.remote

import com.aga.data.data.model.alarm.AlarmDetailResponse
import com.aga.data.data.model.alarmdetail.AlarmDetailModifyRequest
import com.aga.data.data.model.alarmdetail.AlarmDetailRequest

interface AlarmDetailRemoteDataSource {
    suspend fun createNewPersonalAlarm(
        alarmDetailRequest: AlarmDetailRequest
    ): Result<AlarmDetailResponse>

    suspend fun modifyPersonalAlarm(
        alarmDetailModifyRequest: AlarmDetailModifyRequest
    ): Result<AlarmDetailResponse>
}