package com.aga.data.data.repository.alarm

import com.aga.data.data.model.alarm.AlarmDataRequest
import com.aga.data.data.model.mapper.toAlarm
import com.aga.data.data.repository.alarm.remote.AlarmRemoteDataSource
import com.aga.domain.model.Alarm
import com.aga.domain.repository.AlarmRepository
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val alarmRemoteDataSource: AlarmRemoteDataSource
) : AlarmRepository {
    override suspend fun createNewAlarm(
        alarmName: String, alarmDay: String, teamId: Int
    ): Result<Alarm> {
        return alarmRemoteDataSource.createNewAlarm(
            AlarmDataRequest(
                alarmName, alarmDay, teamId
            )
        ).map {
            it.toAlarm()
        }
    }
}