package com.aga.domain.model

import java.io.Serializable

data class AlarmDetail(
    val dayOfWeek: String,
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
    val isVibrateOn: Boolean,
    val isRingtoneOn: Boolean,
    val ringtoneUri: String?
): Serializable