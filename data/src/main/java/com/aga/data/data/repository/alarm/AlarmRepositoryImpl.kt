package com.aga.data.data.repository.alarm

import com.aga.data.data.model.alarm.AlarmDataRequest
import com.aga.data.data.model.mapper.toAlarm
import com.aga.data.data.model.mapper.toAlarmWithDetailList
import com.aga.data.data.model.mapper.toFormattedString
import com.aga.data.data.repository.alarm.remote.AlarmRemoteDataSource
import com.aga.domain.model.Alarm
import com.aga.domain.model.AlarmWithDetailList
import com.aga.domain.repository.AlarmRepository
import java.time.DayOfWeek
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val alarmRemoteDataSource: AlarmRemoteDataSource
) : AlarmRepository {
    override suspend fun createNewAlarm(
        alarmName: String, alarmDay: Set<DayOfWeek>, teamId: Int
    ): Result<Alarm> {
        return alarmRemoteDataSource.createNewAlarm(
            AlarmDataRequest(
                alarmName, alarmDay.toFormattedString(), teamId
            )
        ).map {
            it.toAlarm()
        }
    }

    override suspend fun getAlarmListByTeamId(teamId: Int): Result<List<AlarmWithDetailList>> {
        return alarmRemoteDataSource.getAlarmListByTeamId(
            teamId
        ).map { list->
            list.map { it.toAlarmWithDetailList() }
        }
    }
}