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
        return alarmDetailDao.insertAlarmDetail(alarmDetailEntity)
    }

    override suspend fun getAlarmDetailById(id: Int): AlarmDetail {
        return alarmDetailDao.getAlarmDetailById(id).toAlarmDetail()
    }

    override suspend fun getAlarmDetailByUserId(userId: String): List<AlarmDetail> {
        return alarmDetailDao.getAlarmDetailByUserId(userId).map {
            it.toAlarmDetail()
        }
    }

    override suspend fun getAllAlarmDetail(): List<AlarmDetailEntity> {
        return alarmDetailDao.getAllAlarmDetail()
    }

    override suspend fun deleteAlarmDetailById(id: Int) {
        return alarmDetailDao.deleteAlarmDetailById(id)
    }

    override suspend fun deleteAllAlarmDetail() {
        return alarmDetailDao.deleteAllAlarmDetail()
    }
}