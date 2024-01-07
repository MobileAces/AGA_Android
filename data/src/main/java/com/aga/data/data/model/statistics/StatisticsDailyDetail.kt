package com.aga.data.data.model.statistics

data class StatisticsDailyDetail(
    val userNickname: String,
    val success: Boolean,
    val dateTime: String,
    val wakeupHour: Int,
    val wakeupMinute: Int,
    val wakeupMemo: String?,
    val wakeupForecast: Boolean,
    val wakeupVoice: Boolean
)
