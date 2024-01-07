package com.aga.data.data.repository.statistics.remote

import com.aga.data.data.api.WakeUpService
import com.aga.data.data.model.mapper.toDailyStatistics
import com.aga.data.data.model.mapper.toPeriodStatistics
import com.aga.data.data.model.statistics.PeriodStatisticsRequest
import com.aga.domain.model.DailyStatistics
import com.aga.domain.model.PeriodStatistics
import javax.inject.Inject

class StatisticsRemoteDataSourceImpl @Inject constructor(
    private val wakeUpService: WakeUpService
): StatisticsRemoteDataSource{
    override suspend fun getPeriodStatistics(
        userList: List<String>,
        teamId: Int,
        startDate: String,
        endDate: String
    ): PeriodStatistics {
        val response = wakeUpService.getPeriodStatistics(
            PeriodStatisticsRequest(
                userList,
                teamId,
                startDate,
                endDate
            )
        )
        return if (response.isSuccessful){
            response.body()?.data!!.toPeriodStatistics()
        }else{
            PeriodStatistics(
                listOf(),
                -1,
                -1
            )
        }
    }

    override suspend fun getDailyStatistics(teamId: Int, date: String): List<DailyStatistics> {
        val response = wakeUpService.getDailyStatistics(teamId, date)
        return if (response.isSuccessful)
            response.body()?.dataList!!.map {
                it.toDailyStatistics()
            }
        else
            listOf()
    }

}