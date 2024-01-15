package com.aga.domain.usecase.alarm

import com.aga.domain.model.AlarmDetail
import com.aga.domain.repository.AlarmDetailRepository
import javax.inject.Inject

class ModifyAlarmDetailUseCase @Inject constructor(
    private val alarmDetailRepository: AlarmDetailRepository
) {
    suspend operator fun invoke(alarmDetail: AlarmDetail): Result<AlarmDetail>{
        return alarmDetailRepository.modifyPersonalAlarm(alarmDetail)
    }
}