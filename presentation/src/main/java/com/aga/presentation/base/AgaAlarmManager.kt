package com.aga.presentation.base

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import com.aga.domain.model.AlarmDetail
import com.aga.presentation.alarm.AlarmReceiver
import java.util.Calendar
import java.util.StringTokenizer

object AgaAlarmManager {
    private var alarmManager: AlarmManager? = null

    const val HOUR = 1000 * 60 * 60
    const val MINUTE = 1000 * 60
    const val SECOND = 1000

    fun init(context: Context) {
        if (alarmManager == null)
            alarmManager = context.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
    }

    fun setNewAlarm(alarmDetail: AlarmDetail, context: Context) {
        val tk = StringTokenizer(alarmDetail.dayOfWeek, " ")
        while (tk.hasMoreTokens()){
            val calendar = Calendar.getInstance()

            val day = tk.nextToken().toInt()
            calendar.set(Calendar.DAY_OF_WEEK, day)
            calendar.set(Calendar.HOUR_OF_DAY, alarmDetail.hour)
            calendar.set(Calendar.MINUTE, alarmDetail.minute)
            calendar.set(Calendar.SECOND, 0)
            val timeDiff = calendar.timeInMillis - System.currentTimeMillis()

            if (timeDiff <= 0){
                calendar.add(Calendar.DATE, 7)
            }
            val alarmTime = calendar.timeInMillis
            val reqCode = "${alarmDetail.id}$day".toInt()

            val receiverIntent = Intent(context, AlarmReceiver::class.java)
            receiverIntent.apply {
                putExtra("alarm_detail_id", alarmDetail.id)
                putExtra("isRepeatAlarm", false)
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                reqCode,
                receiverIntent,
                PendingIntent.FLAG_IMMUTABLE
            )
            alarmManager!!.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent)
        }

    }

    fun repeatAlarm(alarmDetail: AlarmDetail, context: Context) {
        var nextAlarmTime = System.currentTimeMillis()
        nextAlarmTime += 1000 * 60 * alarmDetail.repeatTime

        val receiverIntent = Intent(context, AlarmReceiver::class.java)
        receiverIntent.apply {
            putExtra("alarm_detail_id", alarmDetail.id)
            putExtra("isRepeatAlarm", true)
        }

        val reqCode = "${alarmDetail.id}${Calendar.getInstance().get(Calendar.DAY_OF_WEEK)}".toInt()

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reqCode,
            receiverIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager!!.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, nextAlarmTime, pendingIntent)
    }

    fun cancelRepeatAlarm(alarmDetail: AlarmDetail, context: Context){
        val intent = Intent(context, AlarmReceiver::class.java)

        val reqCode = "${alarmDetail.id}${Calendar.getInstance().get(Calendar.DAY_OF_WEEK)}".toInt()

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            PendingIntent.getBroadcast(context,reqCode , intent, PendingIntent.FLAG_IMMUTABLE)
        }else{
            PendingIntent.getBroadcast(context, reqCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        alarmManager!!.cancel(pendingIntent)

        //다시 울리기로 울린 알람은 알람 매니저에서 작업 해제를 하고 원본 알람으로 다시 재등록 해줘야함
        setNewAlarm(alarmDetail, context)
    }

    fun cancelAlarm(alarmDetail: AlarmDetail, context: Context) {
        val tk = StringTokenizer(alarmDetail.dayOfWeek)
        while (tk.hasMoreTokens()) {
            val intent = Intent(context, AlarmReceiver::class.java)
            val day = tk.nextToken().toInt()
            val reqCode = "${alarmDetail.id}$day".toInt()

            val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.getBroadcast(context, reqCode, intent, PendingIntent.FLAG_IMMUTABLE)
            } else {
                PendingIntent.getBroadcast(
                    context,
                    reqCode,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            }

            alarmManager!!.cancel(pendingIntent)
        }
    }

}