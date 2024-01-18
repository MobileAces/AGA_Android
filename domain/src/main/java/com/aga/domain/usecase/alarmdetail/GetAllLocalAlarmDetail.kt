package com.aga.domain.usecase.alarmdetail

import com.aga.domain.model.AlarmDetail
import com.aga.domain.repository.AlarmDetailRepository
import javax.inject.Inject

class GetAllLocalAlarmDetail @Inject constructor(
    private val repository: AlarmDetailRepository
){
    suspend operator fun invoke(): List<AlarmDetail>{
        return repository.getAllAlarmFromLocal()
    }
}