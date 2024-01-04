package com.aga.domain.repository

import com.aga.domain.model.Alarm

interface AlarmRepository {
    suspend fun createNewAlarm(alarmName: String, alarmDay: String, teamId: Int): Result<Alarm>
}