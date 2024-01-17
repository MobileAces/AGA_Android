package com.aga.data.data.model.statistics

data class WakeUpRegisterData(
    val alarmDetailId: Int,
    val alarmDetailHour: Int,
    val alarmDetailMinute: Int,
    val alarmDetailRetime: Int,
    val alarmDetailMemo: String?,
    val alarmDetailForecast: Boolean,
    val alarmDetailIsOn: Boolean,
    val alarmId: Int,
    val userId: String,
    val userNickname: String
)
