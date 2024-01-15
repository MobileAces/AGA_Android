package com.aga.data.data.model.alarm

import com.squareup.moshi.Json

data class AlarmWithDetailListResponse(
    val alarmId: Int,
    val alarmName: String,
    val alarmDay: String,
    val teamId: Int,
    @Json(name = "dataList")
    val alarmDetailList: List<AlarmDetailResponse>,
)