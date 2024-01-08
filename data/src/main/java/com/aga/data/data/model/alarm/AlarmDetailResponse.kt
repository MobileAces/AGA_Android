package com.aga.data.data.model.alarm

/**
 * USE_AT
 *
 * AlarmService
 *
 * .getAlarmListByTeamId
 */
data class AlarmDetailResponse(
    val alarmDetailId: Int,
    val alarmDetailHour: Int,
    val alarmDetailMinute: Int,
    val alarmDetailRetime: Int,
    val alarmDetailMemo: String,
    val alarmDetailForecast: Boolean,
    val alarmDetailMemoVoice: Boolean,
    val alarmDetailIsOn: Boolean,
    val alarmId: Int,
    val userId: String,
    val userNickname: String,
)
