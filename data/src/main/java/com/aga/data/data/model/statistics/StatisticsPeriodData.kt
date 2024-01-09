package com.aga.data.data.model.statistics

data class StatisticsPeriodData(
    val userList: List<StatisticsPeriodUser>,
    val totalSum: Int,
    val totalSuccessSum: Int
)
