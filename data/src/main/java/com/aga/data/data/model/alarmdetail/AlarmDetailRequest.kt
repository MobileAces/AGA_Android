package com.aga.data.data.model.alarmdetail

data class AlarmDetailRequest(
    val alarmDetailHour: Int,
    val alarmDetailMinute: Int,
    val alarmDetailRetime: Int,
    val alarmDetailMemo: String,
    val alarmDetailForecast: Boolean,
    val alarmDetailMemoVoice: Boolean,
    val alarmDetailIsOn: Boolean,
    val alarmId: Int,
    val userId: String,
)
