package com.aga.presentation.alarm

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.aga.domain.model.AlarmDetail
import com.aga.domain.model.WakeUp
import com.aga.presentation.LoginActivity
import com.aga.presentation.base.AgaAlarmManager
import com.aga.presentation.base.BaseActivity
import com.aga.presentation.base.Constants
import com.aga.presentation.base.PrefManager
import com.aga.presentation.databinding.ActivityAlarmRingBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@AndroidEntryPoint
class AlarmRingActivity : BaseActivity<ActivityAlarmRingBinding>(
    ActivityAlarmRingBinding::inflate
) {
    private val viewModel: AlarmRingViewModel by viewModels()
    private lateinit var alarmDetail: AlarmDetail
    private var isRepeatAlarm = false
    @SuppressLint("SimpleDateFormat")
    private val formatter = SimpleDateFormat("MM월 dd일")
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd")

    private lateinit var tts: TextToSpeech
    private var ttsContent = ""

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val responsePermissions = permissions.entries.filter {
                it.key in LoginActivity.locationPermissions
            }

            if (responsePermissions.filter { it.value }.size == LoginActivity.locationPermissions.size) {
//                Toast.makeText(this, "위치 기반 비 예보 서비스를 이용하실 수 있습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "위치 기반 비 예보 서비스를 이용하실 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }

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
        registerObserver()
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
            setMemoTTS()
            stopAlarm()
        }

        binding.btnPostpone.setOnClickListener {
            postponeAlarm()
        }
    }

    private fun registerObserver(){
        viewModel.weatherResult.observe(this){
            if (it.location != "FAIL"){
                if (it.rainAmount > 0){
                    ttsContent = "$ttsContent  오늘 ${it.location}에 비 예보가 있습니다."
                }
            }else{
                //기상 정보 로드 에러
                ttsContent = "$ttsContent  기상 정보를 불러오는데 실패했습니다."
            }
            startTTS()
        }

        viewModel.registerWakeUpResult.observe(this){
            finishRingActivity()
        }

    }

    private fun setMemoTTS(){
        if (alarmDetail.memoVoice){
            ttsContent = alarmDetail.memo
        }
        getWeatherInfo()
    }

    private fun getWeatherInfo(){
        if (alarmDetail.forecast){
            // 마지막 위치 받아오기
            val locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager
            val current = if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionLauncher.launch(locationPermissions)
                return
            }else{
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            }

            // 위도, 경도 (순서대로)
            val longitude = current?.longitude
            val latitude = current?.latitude

            if (longitude != null && latitude != null){
                viewModel.getWeatherInfo(latitude, longitude)
            }
        }else{
            startTTS()
        }
    }

    private fun startTTS(){
        tts = TextToSpeech(this) { p0 ->
            if (p0 != TextToSpeech.ERROR)
                tts.language = Locale.KOREAN
        }
        tts.speak(ttsContent, TextToSpeech.QUEUE_FLUSH, null, TTS_ID)
        registerWakeUpOnServer()
    }

    private fun registerWakeUpOnServer(){
        reScheduleAlarm()

        val curTime = Calendar.getInstance()
        val hour = curTime.get(Calendar.HOUR_OF_DAY)
        val min = curTime.get(Calendar.MINUTE)

        val hourDiff = hour - alarmDetail.hour
        val minDiff = min - alarmDetail.minute
        val pastMin = hourDiff * 60 + minDiff

        // 현재 시간과 알람 설정 시간 비교, 30분 이상 지났으면 success를 false로
        viewModel.registerWakeUp(
            WakeUp(
                pastMin > 30,
                dateFormat.format(System.currentTimeMillis()),
                hour,
                min,
                alarmDetail.memo,
                alarmDetail.forecast,
                alarmDetail.memoVoice,
                PrefManager.read(Constants.PREF_USER_ID, "")!!,
                alarmDetail.teamId,
                alarmDetail.alarmId,
                alarmDetail.id
            )
        )
    }

    private fun reScheduleAlarm(){
        if (isRepeatAlarm){
            AgaAlarmManager.cancelRepeatAlarm(alarmDetail, this)
        }else{
            AgaAlarmManager.cancelAlarm(alarmDetail, this)
            AgaAlarmManager.setNewAlarm(alarmDetail, this)
        }
    }

    private fun stopAlarm() {
        val serviceIntent = Intent(this, AlarmService::class.java)
        stopService(serviceIntent)
        moveTaskToBack(false)
    }

    private fun finishRingActivity(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAndRemoveTask()
        } else {
            finish()
        }
    }

    @SuppressLint("ScheduleExactAlarm")
    fun postponeAlarm(){
        AgaAlarmManager.repeatAlarm(alarmDetail, this@AlarmRingActivity)
        stopAlarm()
        finishRingActivity()
    }

    //뒤로가기 버튼 클릭 무시
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
//        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        tts.stop()
        tts.shutdown()
    }

    companion object{
        val locationPermissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        const val TTS_ID = "aga_tts"
    }
}