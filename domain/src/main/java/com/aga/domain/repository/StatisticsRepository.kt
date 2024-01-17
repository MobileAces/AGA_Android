package com.aga.domain.repository

import com.aga.domain.model.DailyStatistics
import com.aga.domain.model.PeriodStatistics
import com.aga.domain.model.WakeUp

interface StatisticsRepository {
    suspend fun getPeriodStatistics(userList: List<String>, teamId: Int, startDate: String, endDate: String): PeriodStatistics

    suspend fun getDailyStatistics(teamId: Int, date: String): List<DailyStatistics>

    suspend fun registerWakeUp(wakeUp: WakeUp): Boolean
}