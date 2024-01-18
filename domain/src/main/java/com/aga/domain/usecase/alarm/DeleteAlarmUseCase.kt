package com.aga.domain.usecase.alarm

import com.aga.domain.repository.AlarmRepository
import javax.inject.Inject

class DeleteAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {
    suspend operator fun invoke(id: Int): Result<Boolean>{
        return alarmRepository.deleteAlarmById(id)
    }
}