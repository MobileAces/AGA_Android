package com.aga.domain.model

data class DailyStatisticsDetail(
    val userNickname: String,
    val success: Boolean,
    val dateTime: String,
    val wakeupHour: Int,
    val wakeupMinute: Int,
    val wakeupMemo: String?,
    val wakeupForecast: Boolean,
    val wakeupVoice: Boolean
)
