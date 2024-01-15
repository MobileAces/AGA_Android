package com.aga.domain.usecase.statistics

import com.aga.domain.model.PeriodStatistics
import com.aga.domain.repository.StatisticsRepository
import javax.inject.Inject

class GetPeriodStatisticsUseCase @Inject constructor(
    private val repository: StatisticsRepository
) {
    suspend operator fun invoke(userList: List<String>, teamId: Int, startDate: String, endDate: String): PeriodStatistics{
        return repository.getPeriodStatistics(userList, teamId, startDate, endDate)
    }
}