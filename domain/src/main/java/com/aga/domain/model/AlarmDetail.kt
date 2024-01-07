package com.aga.domain.model

data class AlarmDetail(
    val alarmDetailId: Int,
    val alarmDetailHour: Int,
    val alarmDetailMinute: Int,
    val alarmDetailRetime: Int,
    val alarmDetailMemo: String,
    val alarmDetailForecast: Boolean,
    val alarmDetailMemoVoice: Boolean,
    val alarmId: Int,
    val userId: String,
    val userNickname: String,
)