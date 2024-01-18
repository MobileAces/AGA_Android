package com.aga.domain.repository

import com.aga.domain.model.AlarmDetail

interface AlarmDetailRepository {
    suspend fun createNewPersonalAlarm(
        alarmDetail: AlarmDetail
    ): Result<AlarmDetail>

    suspend fun modifyPersonalAlarm(
        alarmDetail: AlarmDetail
    ): Result<AlarmDetail>

    suspend fun getSavedAlarmDetailById(
        id: Int
    ): AlarmDetail?
}