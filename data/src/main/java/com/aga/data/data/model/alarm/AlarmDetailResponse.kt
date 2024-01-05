package com.aga.data.data.model.alarm

data class AlarmDetailResponse(
    val alarmDetailId: Int,
    val alarmDetailHour: Int,
    val alarmDetailMinute: Int,
    val alarmDetailRetime: Int,
    val alarmDetailMemo: String,
    val alarmDetailForecast: Boolean,
    val alarmDetailMemoVoice: Boolean,
    val alarmId: Int,
    val userId: String
)