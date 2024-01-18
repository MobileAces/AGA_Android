package com.aga.data.data.repository.alarmdetail.local

import com.aga.data.data.model.alarmdetail.AlarmDetailEntity

interface AlarmDetailLocalDataSource {
    suspend fun insertAlarmDetail(alarmDetailEntity: AlarmDetailEntity)

    suspend fun getAlarmDetailById(id: Int): AlarmDetailEntity

    suspend fun getAlarmDetailByUserId(userId: String): List<AlarmDetailEntity>

    suspend fun getAllAlarmDetail(): List<AlarmDetailEntity>

    suspend fun deleteAlarmDetailById(id: Int)

    suspend fun deleteAllAlarmDetail()

    suspend fun updateAlarmDetail(alarmDetailEntity: AlarmDetailEntity)
}