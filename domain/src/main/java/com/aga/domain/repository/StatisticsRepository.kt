package com.aga.domain.repository

import com.aga.domain.model.PeriodStatistics

interface StatisticsRepository {
    suspend fun getPeriodStatistics(userList: List<String>, teamId: Int, startDate: String, endDate: String): PeriodStatistics
}