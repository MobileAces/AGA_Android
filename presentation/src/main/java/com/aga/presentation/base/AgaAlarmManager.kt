package com.aga.presentation.base

import android.app.AlarmManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity

object AgaAlarmManager {
    private var alarmManager: AlarmManager? = null

    fun init(context: Context){
        if (alarmManager == null)
            alarmManager = context.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
    }

    fun setNewAlarm(alarmDetailId: Int){

    }
}