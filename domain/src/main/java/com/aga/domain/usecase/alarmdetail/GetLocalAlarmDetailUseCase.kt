package com.aga.domain.usecase.alarmdetail

import com.aga.domain.model.AlarmDetail
import com.aga.domain.repository.AlarmDetailRepository
import javax.inject.Inject

class GetLocalAlarmDetailUseCase @Inject constructor(
    private val repository: AlarmDetailRepository
) {
    suspend operator fun invoke(id: Int): AlarmDetail{
        return repository.getAlarmDetailFromLocal(id)
    }
}