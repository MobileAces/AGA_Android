package com.aga.domain.repository

import com.aga.domain.model.AlarmDetail

interface AlarmDetailRepository {
    suspend fun createNewPersonalAlarm(
        alarmDetail: AlarmDetail
    ): Result<AlarmDetail>

    suspend fun modifyPersonalAlarm(
        alarmDetail: AlarmDetail
    ): Result<AlarmDetail>

    suspend fun getAlarmDetailFromLocal(
        id: Int
    ): AlarmDetail

    suspend fun getAllAlarmFromLocal(): List<AlarmDetail>

    suspend fun isNotDeletedAlarm(
        id: Int
    ): Boolean

    suspend fun deleteAlarmDetailInLocal(
        id: Int
    )
}