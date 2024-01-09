package com.aga.domain.model

data class DailyStatistics(
    val alarmId: Int,
    val alarmName: String,
    val wakeupList: List<DailyStatisticsDetail>
)
