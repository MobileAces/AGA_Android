package com.aga.data.data.repository.alarmdetail.remote

import com.aga.data.data.api.AlarmDetailService
import com.aga.data.data.model.alarm.AlarmDetailResponse
import com.aga.data.data.model.alarmdetail.AlarmDetailModifyRequest
import com.aga.data.data.model.alarmdetail.AlarmDetailRequest
import javax.inject.Inject

class AlarmDetailRemoteDataSourceImpl @Inject constructor(
    private val alarmDetailService: AlarmDetailService
): AlarmDetailRemoteDataSource{
    override suspend fun createNewPersonalAlarm(alarmDetailRequest: AlarmDetailRequest): Result<AlarmDetailResponse> {
        return alarmDetailService.createNewPersonalAlarm(alarmDetailRequest).let {
            if (it.isSuccessful && it.body() != null){
                Result.success(it.body()!!.data!!)
            } else {
                Result.failure(Exception(it.code().toString()))
            }
        }
    }

    override suspend fun modifyPersonalAlarm(alarmDetailModifyRequest: AlarmDetailModifyRequest): Result<AlarmDetailResponse> {
        return alarmDetailService.modifyPersonalAlarm(alarmDetailModifyRequest).let {
            if (it.isSuccessful && it.body() != null){
                Result.success(it.body()!!.data!!)
            } else {
                Result.failure(Exception(it.code().toString()))
            }
        }
    }

    override suspend fun getAlarmDetailById(id: Int): Boolean {
        return alarmDetailService.getAlarmDetailById(id).let {
            it.isSuccessful && it.body() != null
        }
    }
}