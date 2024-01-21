package com.aga.data.data.model.statistics

data class WakeUpRegisterRequest(
    val success: Boolean,
    val datetime: String,
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
