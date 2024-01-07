package com.aga.data.data.model.statistics

data class PeriodStatisticsRequest(
    val userNicknameList: List<String>,
    val teamId: Int,
    val startDate: String,
    val endDate: String
)
