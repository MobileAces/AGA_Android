package com.aga.domain.usecase.alarm

import com.aga.domain.model.Alarm
import com.aga.domain.repository.AlarmRepository
import javax.inject.Inject

class CreateAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {
    suspend operator fun invoke(alarmName: String, alarmDay: String, teamId: Int): Result<Alarm> {
        return alarmRepository.createNewAlarm(alarmName, alarmDay, teamId)
    }
}