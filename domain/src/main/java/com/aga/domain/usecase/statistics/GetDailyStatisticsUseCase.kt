package com.aga.domain.usecase.statistics

import com.aga.domain.model.DailyStatistics
import com.aga.domain.repository.StatisticsRepository
import javax.inject.Inject

class GetDailyStatisticsUseCase @Inject constructor(
    private val repository: StatisticsRepository
) {
    suspend operator fun invoke(teamId: Int, date: String): List<DailyStatistics>{
        return repository.getDailyStatistics(teamId, date)
    }
}