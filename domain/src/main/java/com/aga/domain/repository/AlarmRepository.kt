package com.aga.domain.repository

import com.aga.domain.model.Alarm
import java.time.DayOfWeek

interface AlarmRepository {
    suspend fun createNewAlarm(alarmName: String, alarmDay: Set<DayOfWeek>, teamId: Int): Result<Alarm>
}