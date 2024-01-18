package com.aga.domain.usecase.alarmdetail

import com.aga.domain.repository.AlarmDetailRepository
import javax.inject.Inject

class DeleteAlarmDetailFromLocalUseCase @Inject constructor(
    private val repository: AlarmDetailRepository
) {
    suspend operator fun invoke(id: Int){
        repository.deleteAlarmDetailInLocal(id)
    }
}