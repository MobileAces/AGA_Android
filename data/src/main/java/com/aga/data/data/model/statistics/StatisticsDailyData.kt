package com.aga.data.data.model.statistics

data class StatisticsDailyData(
    val alarmId: Int,
    val alarmName: String,
    val wakeupList: List<StatisticsDailyDetail>
)
