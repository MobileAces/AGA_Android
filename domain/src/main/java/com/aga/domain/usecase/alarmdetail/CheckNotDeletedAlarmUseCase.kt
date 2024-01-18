package com.aga.domain.usecase.alarmdetail

import com.aga.domain.repository.AlarmDetailRepository
import javax.inject.Inject

class CheckNotDeletedAlarmUseCase @Inject constructor(
    private val repository: AlarmDetailRepository
) {
    suspend operator fun invoke(id: Int): Boolean{
        return repository.isNotDeletedAlarm(id)
    }
}