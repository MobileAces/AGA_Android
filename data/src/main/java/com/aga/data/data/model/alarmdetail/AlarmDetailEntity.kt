package com.aga.data.data.model.alarmdetail

import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "alarm_detail_table", primaryKeys = ["id"])
data class AlarmDetailEntity(
    val dayOfWeek: Int,
    val id: Int,
    val hour: Int,
    val minute: Int,
    val repeatTime: Int,
    val memo: String,
    val forecast: Boolean,
    val memoVoice: Boolean,
    val isOn: Boolean,
    val alarmId: Int,
    val userId: String,
    val userNickname: String,
    val isVibrateOn: Boolean,
    val isRingtoneOn: Boolean,
    val ringtoneUri: String?
): Serializable