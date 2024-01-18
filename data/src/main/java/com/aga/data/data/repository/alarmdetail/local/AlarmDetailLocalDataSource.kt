package com.aga.data.data.repository.alarmdetail.local

import com.aga.data.data.model.alarmdetail.AlarmDetailEntity
import com.aga.domain.model.AlarmDetail

interface AlarmDetailLocalDataSource {
    suspend fun insertAlarmDetail(alarmDetailEntity: AlarmDetailEntity)

    suspend fun getAlarmDetailById(id: Int): AlarmDetail

    suspend fun getAlarmDetailByUserId(userId: String): List<AlarmDetail>

    suspend fun getAllAlarmDetail(): List<AlarmDetail>

    suspend fun deleteAlarmDetailById(id: Int)

    suspend fun deleteAllAlarmDetail()
}