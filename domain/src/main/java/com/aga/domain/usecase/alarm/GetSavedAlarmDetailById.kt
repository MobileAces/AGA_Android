package com.aga.domain.usecase.alarm

import com.aga.domain.model.AlarmDetail
import com.aga.domain.repository.AlarmDetailRepository
import javax.inject.Inject

class GetSavedAlarmDetailById @Inject constructor(
    private val alarmDetailRepository: AlarmDetailRepository
){
    suspend operator fun invoke(id: Int): AlarmDetail? {
        return alarmDetailRepository.getSavedAlarmDetailById(id)
    }
}