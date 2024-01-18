package com.aga.domain.usecase.alarmdetail

import com.aga.domain.model.AlarmDetail
import com.aga.domain.repository.AlarmDetailRepository
import javax.inject.Inject

class GetAllLocalAlarmDetailByUserId @Inject constructor(
    private val repository: AlarmDetailRepository
){
    suspend operator fun invoke(userId: String): List<AlarmDetail>{
        return repository.getAllAlarmByUserIdFromLocal(userId)
    }
}