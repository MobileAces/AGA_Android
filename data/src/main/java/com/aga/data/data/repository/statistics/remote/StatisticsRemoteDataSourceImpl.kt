package com.aga.data.data.repository.statistics.remote

import com.aga.data.data.api.WakeUpService
import com.aga.data.data.model.mapper.toDailyStatistics
import com.aga.data.data.model.mapper.toPeriodStatistics
import com.aga.data.data.model.mapper.toWakeUpRegisterRequest
import com.aga.data.data.model.statistics.PeriodStatisticsRequest
import com.aga.data.data.model.statistics.WakeUpRegisterRequest
import com.aga.domain.model.DailyStatistics
import com.aga.domain.model.PeriodStatistics
import com.aga.domain.model.WakeUp
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
            response.body()?.dataList!!.filter { it.wakeupList.isNotEmpty() }.map {
                it.toDailyStatistics()
            }
        else
            listOf()
    }

    override suspend fun registerWakeUp(wakeUp: WakeUp): Boolean {
        return wakeUpService.registerWakeUp(wakeUp.toWakeUpRegisterRequest()).isSuccessful
    }


}