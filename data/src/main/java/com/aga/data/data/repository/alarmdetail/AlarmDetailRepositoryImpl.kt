package com.aga.data.data.repository.alarmdetail

import com.aga.data.data.model.mapper.toAlarmDetail
import com.aga.data.data.model.mapper.toAlarmDetailEntity
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
        }.also { result ->
            result.getOrNull()?.also {
                alarmDetailLocalDataSource.insertAlarmDetail(alarmDetail.copy(id = it.id).toAlarmDetailEntity())
            }
        }
    }

    override suspend fun modifyPersonalAlarm(alarmDetail: AlarmDetail): Result<AlarmDetail> {
        return if (alarmDetail.teamId < 0){
            alarmDetailLocalDataSource.getAlarmDetailById(alarmDetail.id).copy(isOn = alarmDetail.isOn)
        } else {
            alarmDetail
        } .let {
            alarmDetailRemoteDataSource.modifyPersonalAlarm(it.toAlarmDetailModifyRequest()).map {response ->
                response.toAlarmDetail()
            }.also {result ->
                if (result.isSuccess){
                    alarmDetailLocalDataSource.updateAlarmDetail(it.toAlarmDetailEntity())
                }
            }
        }
    }

    override suspend fun getAlarmDetailFromLocal(id: Int): AlarmDetail {
        return alarmDetailLocalDataSource.getAlarmDetailById(id)
    }

    override suspend fun getAllAlarmFromLocal(): List<AlarmDetail> {
        return alarmDetailLocalDataSource.getAllAlarmDetail()
    }

    override suspend fun isNotDeletedAlarm(id: Int): Boolean {
        return alarmDetailRemoteDataSource.getAlarmDetailById(id)
    }

    override suspend fun deleteAlarmDetailInLocal(id: Int) {
        alarmDetailLocalDataSource.deleteAlarmDetailById(id)
    }


    override suspend fun getSavedAlarmDetailById(id: Int): AlarmDetail? {
        return alarmDetailLocalDataSource.getAlarmDetailById(id)
    }
}