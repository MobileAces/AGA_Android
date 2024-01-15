package com.aga.presentation.alarm

import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.os.Vibrator
import android.util.Log
import com.aga.domain.model.AlarmDetail


class AlarmService: Service() {
    private lateinit var manager: NotificationManager
    private lateinit var builder: Notification.Builder
    private lateinit var notification: Notification

    private var isRepeatAlarm = false
    private lateinit var alarmDetail: AlarmDetail

    private var audioManager: AudioManager? = null
    private var mediaPlayer: MediaPlayer? = null
    private var vibrator: Vibrator? = null
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        setNotification();
        startForeground(40001, notification);
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        isRepeatAlarm = intent?.getBooleanExtra("isRepeatAlarm", false)!!
        alarmDetail = intent?.getSerializableExtra("alarm_detail") as AlarmDetail

        setAudioManager()
        return START_STICKY
    }

    private fun startAlarm(){
        val intent = Intent(this, AlarmRingActivity::class.java)
        intent.apply {
            putExtra("isRepeatAlarm", isRepeatAlarm)
            putExtra("alarm_detail", alarmDetail)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    private fun setAudioManager() {
        if (alarmDetail.isRingtoneOn) {
            audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
            val maxVol = audioManager!!.getStreamMaxVolume(AudioManager.STREAM_ALARM)
            val volume: Int = 80
            audioManager!!.setStreamVolume(
                AudioManager.STREAM_ALARM,
                maxVol * volume / 100,
                AudioManager.FLAG_PLAY_SOUND
            )
          startRingtone()
        }
    }

    private fun startRingtone() {
        if(alarmDetail.isRingtoneOn){
            val basicUri = Uri.parse(alarmDetail.ringtoneUri)
            try {
                mediaPlayer!!.setDataSource(applicationContext, basicUri)
                mediaPlayer!!.isLooping = true
                mediaPlayer!!.setOnPreparedListener { mp: MediaPlayer -> mp.start() }
                mediaPlayer!!.setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val audioAttributes = AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .build()
                    mediaPlayer!!.setAudioAttributes(audioAttributes)
                } else {
                    mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_ALARM)
                }
                mediaPlayer!!.prepareAsync()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        setVibrate()
    }


    private fun setVibrate() {
        if (alarmDetail.isVibrateOn) {
            Thread {
                vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
                val pattern = longArrayOf(1000, 1000, 1000, 1000)
                val REPEAT_VIBRATE = 0 // 0:반복, -1:반복x
                vibrator?.vibrate(pattern, REPEAT_VIBRATE)
            }.start()
        }
        startAlarm()
    }

    private fun stopMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer!!.release()
            mediaPlayer = null
        }
    }

    private fun stopVibrate() {
        vibrator?.cancel()
    }

    override fun onDestroy() {
        // 서비스 종료 시, 호출
        super.onDestroy()
        stopMediaPlayer()
        stopVibrate()
    }

    private fun setNotification() {
        val intent = Intent(this, AlarmRingActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0,
            intent, PendingIntent.FLAG_IMMUTABLE
        )
        if (Build.VERSION.SDK_INT >= 26) {
            manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager.createNotificationChannel(notificationChannel)
            builder = Notification.Builder(this, CHANNEL_ID)
        } else {
            builder = Notification.Builder(this)
        }
        setNotificationBuilder(pendingIntent)
    }

    private fun setNotificationBuilder(pendingIntent: PendingIntent?) {
        builder.setContentTitle("기상 메이트")
            .setTicker("알람 on")
            .setSmallIcon(R.drawable.ic_delete)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.alert_light_frame, "알람 해제하기", pendingIntent)
        notification = builder.build()
        notification.flags = Notification.FLAG_AUTO_CANCEL
    }

    companion object{
        const val CHANNEL_ID = "aga_alarm_id"
        const val CHANNEL_NAME = "aga_alarm_name"
    }

}