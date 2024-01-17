package com.aga.presentation.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.aga.domain.usecase.alarmdetail.GetLocalAlarmDetailUseCase
import com.aga.presentation.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver @Inject constructor(
    private val getLocalAlarmDetailUseCase: GetLocalAlarmDetailUseCase
): BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val id = intent?.getIntExtra("alarm_detail_id", -1)!!
        val isRepeat = intent.getBooleanExtra("isRepeatAlarm", false)

        getAlarmDetail(id, isRepeat, context)
    }

    private fun getAlarmDetail(alarmDetailId: Int, isRepeat: Boolean,  context: Context?){
        //Room 에서 알람 디테일 정보 가져오기
        CoroutineScope(Dispatchers.IO).launch {
            val alarmDetail = getLocalAlarmDetailUseCase.invoke(alarmDetailId)
            val serviceIntent = Intent(context, AlarmService::class.java)
            serviceIntent.apply {
                putExtra("alarm_detail", alarmDetail)
                putExtra("isRepeatAlarm", isRepeat)
            }

            context!!.startForegroundService(serviceIntent)
        }

    }
}