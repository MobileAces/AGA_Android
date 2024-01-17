package com.aga.data.data.repository.statistics.remote

import com.aga.data.data.model.statistics.WakeUpRegisterRequest
import com.aga.domain.model.DailyStatistics
import com.aga.domain.model.PeriodStatistics
import com.aga.domain.model.WakeUp

interface StatisticsRemoteDataSource {

    suspend fun getPeriodStatistics(userList: List<String>, teamId: Int, startDate: String, endDate: String): PeriodStatistics

    suspend fun getDailyStatistics(teamId: Int, date: String): List<DailyStatistics>

    suspend fun registerWakeUp(wakeUp: WakeUp): Boolean
}