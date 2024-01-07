package com.aga.data.data.model.alarm

/**
 * USE_AT
 *
 * AlarmService
 *
 * .getAlarmListByTeamId
 */
data class AlarmWithDetailListResponse(
    val alarmId: Int,
    val alarmName: String,
    val alarmDay: String,
    val teamId: Int,
    val alarmDetailList: List<AlarmDetailResponse>,
)