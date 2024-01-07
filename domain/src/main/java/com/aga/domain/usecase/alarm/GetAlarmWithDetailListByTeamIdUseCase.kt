package com.aga.domain.usecase.alarm

import com.aga.domain.model.AlarmWithDetailList
import com.aga.domain.repository.AlarmRepository
import javax.inject.Inject

class GetAlarmWithDetailListByTeamIdUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {
    suspend operator fun invoke(teamId: Int): Result<List<AlarmWithDetailList>>{
        return alarmRepository.getAlarmListByTeamId(teamId)
    }
}