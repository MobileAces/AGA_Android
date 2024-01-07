package com.aga.data.data.repository.statistics.remote

import com.aga.domain.model.PeriodStatistics

interface StatisticsRemoteDataSource {

    suspend fun getPeriodStatistics(userList: List<String>, teamId: Int, startDate: String, endDate: String): PeriodStatistics
}