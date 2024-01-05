package com.aga.domain.model

import java.time.DayOfWeek

data class Alarm(
    val alarmId: Int,
    val alarmName: String,
    val alarmDay: Set<DayOfWeek>,
    val teamId: Int
)
