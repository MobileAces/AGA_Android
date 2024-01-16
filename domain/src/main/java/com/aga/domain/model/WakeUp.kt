package com.aga.domain.model

data class WakeUp(
    val success: Boolean,
    val dateTime: String,
    val wakeupHour: Int,
    val wakeupMinute: Int,
    val wakeupMemo : String?,
    val wakeupForecast: Boolean,
    val wakeupVoice: Boolean,
    val userId: String,
    val teamId: Int,
    val alarmId: Int,
    val alarmDetailId: Int
)
