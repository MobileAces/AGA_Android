package com.aga.presentation.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.aga.domain.model.AlarmDetail
import com.aga.presentation.R
import com.aga.presentation.base.AgaAlarmManager
import com.aga.presentation.base.BaseActivity
import com.aga.presentation.databinding.ActivityAlarmRingBinding
import java.text.SimpleDateFormat

class AlarmRingActivity : BaseActivity<ActivityAlarmRingBinding>(
    ActivityAlarmRingBinding::inflate
) {
    private lateinit var alarmDetail: AlarmDetail
    private var isRepeatAlarm = false
    @SuppressLint("SimpleDateFormat")
    private val formatter = SimpleDateFormat("MM월 dd일")

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                    or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                    or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
        )

        val today = System.currentTimeMillis()

        isRepeatAlarm = intent.getBooleanExtra("isRepeatAlarm", false)
        alarmDetail = intent.getSerializableExtra("alarm_detail") as AlarmDetail
        binding.tvDate.text = formatter.format(today)
        binding.tvAlarmTime.text = "${alarmDetail.hour}:${alarmDetail.minute}"

        registerListener()
    }

    private fun registerListener(){
        binding.btnOff.setOnClickListener {
            /*
            알람 끄는 로직
            1. 메모 있는지 확인
            1-1 있으면 전역변수에 메모 내용 저장
            2. 비 예보 설정했는지 확인
            2-1 비 예보 받으면 weather 조회
            2-2 rainAmount > 0 이면 전역변수에 비 예보 있다고 추가
            3. tts로 알릴 거 있으면 알리기
            4. wakeup 등록
            5. 반복알람인지 확인
            5-1 반복알람이면 원본 알람 조회 후 재등록(manager.cancelRepeatAlarm())
             */
            stopAlarmAndFinishApp()
        }

        binding.btnPostpone.setOnClickListener {
            postponeAlarm()
        }
    }

    private fun stopAlarmAndFinishApp() {
        val serviceIntent = Intent(this, AlarmService::class.java)
        stopService(serviceIntent)
        moveTaskToBack(false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAndRemoveTask()
        } else {
            finish()
        }
    }

    @SuppressLint("ScheduleExactAlarm")
    fun postponeAlarm(){
        AgaAlarmManager.repeatAlarm(alarmDetail, this@AlarmRingActivity)
        stopAlarmAndFinishApp()
    }

    //뒤로가기 버튼 클릭 무시
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
//        super.onBackPressed()
    }
}