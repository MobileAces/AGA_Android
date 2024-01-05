package com.aga.data.data.model.alarm

/**
 * USE_AT
 *
 * AlarmService
 *
 * .createNewAlarm()
 */
data class AlarmDataRequest(
    val alarmName: String,
    val alarmDay: String,
    val teamId: Int
)
