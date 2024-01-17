package com.aga.domain.model

data class AlarmDetail(
    val id: Int,
    val hour: Int,
    val minute: Int,
    val repeatTime: Int,
    val memo: String,
    val forecast: Boolean,
    val memoVoice: Boolean,
    var isOn: Boolean,
    val alarmId: Int,
    val userId: String,
    val userNickname: String,
)