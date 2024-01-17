package com.aga.data.data.repository.alarmdetail

import com.aga.data.data.model.mapper.toAlarmDetail
import com.aga.data.data.model.mapper.toAlarmDetailModifyRequest
import com.aga.data.data.model.mapper.toAlarmDetailRequest
import com.aga.data.data.repository.alarmdetail.local.AlarmDetailLocalDataSource
import com.aga.data.data.repository.alarmdetail.remote.AlarmDetailRemoteDataSource
import com.aga.domain.model.AlarmDetail
import com.aga.domain.repository.AlarmDetailRepository
import javax.inject.Inject

class AlarmDetailRepositoryImpl @Inject constructor(
    private val alarmDetailRemoteDataSource: AlarmDetailRemoteDataSource,
    private val alarmDetailLocalDataSource: AlarmDetailLocalDataSource
) : AlarmDetailRepository {
    override suspend fun createNewPersonalAlarm(alarmDetail: AlarmDetail): Result<AlarmDetail> {
        return alarmDetailRemoteDataSource.createNewPersonalAlarm(
            alarmDetail.toAlarmDetailRequest()
        ).map {
            it.toAlarmDetail()
        }
    }

    override suspend fun modifyPersonalAlarm(alarmDetail: AlarmDetail): Result<AlarmDetail> {
        return alarmDetailRemoteDataSource.modifyPersonalAlarm(
            alarmDetail.toAlarmDetailModifyRequest()
        ).map {
            it.toAlarmDetail()
        }
    }

    override suspend fun getAlarmDetailFromLocal(id: Int): AlarmDetail {
        return alarmDetailLocalDataSource.getAlarmDetailById(id)
    }

}