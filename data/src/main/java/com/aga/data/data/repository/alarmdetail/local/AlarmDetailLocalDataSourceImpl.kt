package com.aga.data.data.repository.alarmdetail.local

import com.aga.data.data.dao.AlarmDetailDao
import com.aga.data.data.model.alarmdetail.AlarmDetailEntity
import com.aga.data.data.model.mapper.toAlarmDetail
import com.aga.domain.model.AlarmDetail
import javax.inject.Inject

class AlarmDetailLocalDataSourceImpl @Inject constructor(
    private val alarmDetailDao: AlarmDetailDao
): AlarmDetailLocalDataSource {
    override suspend fun insertAlarmDetail(alarmDetailEntity: AlarmDetailEntity) {
        alarmDetailDao.insertAlarmDetail(alarmDetailEntity)
    }

    override suspend fun getAlarmDetailById(id: Int): AlarmDetail {
        return alarmDetailDao.getAlarmDetailById(id).toAlarmDetail()
    }

    override suspend fun getAlarmDetailByUserId(userId: String): List<AlarmDetail> {
        return alarmDetailDao.getAlarmDetailByUserId(userId).map {
            it.toAlarmDetail()
        }
    }

    override suspend fun getAllAlarmDetail(): List<AlarmDetail> {
        return alarmDetailDao.getAllAlarmDetail().map {
            it.toAlarmDetail()
        }
    }

    override suspend fun deleteAlarmDetailById(id: Int) {
        alarmDetailDao.deleteAlarmDetailById(id)
    }

    override suspend fun deleteAllAlarmDetail() {
        alarmDetailDao.deleteAllAlarmDetail()
    }

    override suspend fun updateAlarmDetail(alarmDetailEntity: AlarmDetailEntity) {
        alarmDetailDao.updateAlarmDetail(alarmDetailEntity)
    }
}