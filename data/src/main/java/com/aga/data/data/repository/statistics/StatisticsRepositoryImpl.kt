package com.aga.data.data.repository.statistics

import com.aga.data.data.repository.statistics.remote.StatisticsRemoteDataSource
import com.aga.domain.model.DailyStatistics
import com.aga.domain.model.PeriodStatistics
import com.aga.domain.model.WakeUp
import com.aga.domain.repository.StatisticsRepository
import javax.inject.Inject

class StatisticsRepositoryImpl @Inject constructor(
    private val remoteDataSource: StatisticsRemoteDataSource
): StatisticsRepository {
    override suspend fun getPeriodStatistics(
        userList: List<String>,
        teamId: Int,
        startDate: String,
        endDate: String
    ): PeriodStatistics {
        return remoteDataSource.getPeriodStatistics(userList, teamId, startDate, endDate)
    }

    override suspend fun getDailyStatistics(teamId: Int, date: String): List<DailyStatistics> {
        return remoteDataSource.getDailyStatistics(teamId, date)
    }

    override suspend fun registerWakeUp(wakeUp: WakeUp): Boolean {
        return remoteDataSource.registerWakeUp(wakeUp)
    }

}