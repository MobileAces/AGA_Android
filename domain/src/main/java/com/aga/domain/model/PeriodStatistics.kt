package com.aga.domain.model

data class PeriodStatistics(
    val userList: List<PeriodStatisticsUser>,
    val totalSum: Int,
    val totalSuccessSum: Int
)