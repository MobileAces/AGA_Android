package com.aga.presentation.alarm

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.aga.domain.model.AlarmDetail
import com.aga.presentation.MainActivity
import com.aga.presentation.R
import com.aga.presentation.base.AgaAlarmManager
import com.aga.presentation.base.BaseFragment
import com.aga.presentation.base.Constants
import com.aga.presentation.base.PrefManager
import com.aga.presentation.databinding.FragmentAlarmSettingBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

private val TAG = "AlarmSettingFragment_awesome"
@AndroidEntryPoint
class AlarmSettingFragment : BaseFragment<FragmentAlarmSettingBinding>(
    FragmentAlarmSettingBinding::bind, R.layout.fragment_alarm_setting
) {

    private lateinit var mainActivity: MainActivity
    private val alarmViewModel: AlarmViewModel by activityViewModels()
    private val alarmSettingViewModel: AlarmSettingViewModel by viewModels()
    private lateinit var weekDaySelectManager: WeekDaySelectManager
    private lateinit var alarmTime: AlarmTime

    private var repeat: Int = 0
    private var ringtoneUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = requireActivity() as MainActivity
        mainActivity.onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    mainActivity.navigate(Constants.ALARMSETTING_TO_ALARM)
                }
            })
        weekDaySelectManager = WeekDaySelectManager(binding.layoutSelectWeek)

        initUi()
        registerListener()
        registerObserve()
    }


    private fun initUi() {
        setNumberpicker()
        // Alarm 관련
        alarmViewModel.selectedAlarm?.let {alarm ->
            binding.tvAlarmTitle.text = alarm.alarmName
            weekDaySelectManager.setSelectedDay(alarm.alarmDay)
            Log.d(TAG, "initUi: ${alarm.alarmDay}")
        }
        weekDaySelectManager.setAllEnable(false)

        alarmTime = AlarmTime(6,0)
        binding.npHour.value = alarmTime.hour_12
        binding.npMinute.value = alarmTime.minute
        binding.npAmpm.value =
            if (alarmTime.ampm == "오전") {
                0
            } else {
                1
            }

        alarmViewModel.selectedAlarmDetailId?.let {
            alarmSettingViewModel.getAlarmDetailById(it)
        }
    }

    private fun registerListener() {
        binding.ivRepeatMinus.setOnClickListener {
            if (repeat > 0) {
                repeat--
                binding.tvRepeat.text = "${repeat}분"
            }
        }

        binding.ivRepeatPlus.setOnClickListener {
            if (repeat < 10) {
                repeat++
                binding.tvRepeat.text = "${repeat}분"
            }

        }

        // 알람 저장 버튼
        binding.btnSaveSetting.setOnClickListener {
            val userId = PrefManager.read(Constants.PREF_USER_ID, null)
            if (alarmViewModel.selectedAlarm != null && userId != null) {
                alarmViewModel.selectedAlarmDetailId?.let {
                    alarmSettingViewModel.modifyAlarmDetail(
                        AlarmDetail(
                            weekDaySelectManager.getStringForAlarmDetailDayOfWeek(),
                            it,
                            alarmTime.hour_24,
                            alarmTime.minute,
                            repeat,
                            binding.tilMemo.editText!!.text.toString(),
                            binding.switchRainVoice.isChecked,
                            binding.switchMemoVoice.isChecked,
                            true,
                            alarmViewModel.selectedAlarm!!.alarmId,
                            userId,
                            "",
                            binding.switchVibrate.isChecked,
                            binding.switchRing.isChecked,
                            ringtoneUri?.toString() ?: "",
                            alarmViewModel.selectedAlarm!!.teamId
                        ).also { AgaAlarmManager.setNewAlarm(it,requireContext()) }
                    )
                } ?: run {
                    alarmSettingViewModel.createNewAlarm(
                        AlarmDetail(
                            weekDaySelectManager.getStringForAlarmDetailDayOfWeek(),
                            -1,
                            alarmTime.hour_24,
                            alarmTime.minute,
                            repeat,
                            binding.tilMemo.editText!!.text.toString(),
                            binding.switchRainVoice.isChecked,
                            binding.switchMemoVoice.isChecked,
                            true,
                            alarmViewModel.selectedAlarm!!.alarmId,
                            userId,
                            "",
                            binding.switchVibrate.isChecked,
                            binding.switchRing.isChecked,
                            ringtoneUri?.toString() ?: "",
                            alarmViewModel.selectedAlarm!!.teamId
                        ).also { AgaAlarmManager.setNewAlarm(it,requireContext()) }
                    )
                }
            } else {
                showToast("올바르지 않은 접근입니다.")
            }
        }

        // 알람 벨소리 설정 텍스트 뷰
        binding.tvAlarmRingName.setOnClickListener {
            Log.d(TAG, "registerListener: alarmName clicked")
            setAlarmRingtone()
        }

        // 알람 벨소리 스위치
        binding.switchRing.setOnCheckedChangeListener { buttonView, isChecked ->
            Log.d(TAG, "registerListener: switch clicked")
            if (isChecked && ringtoneUri == null){
                AlertDialog.Builder(requireContext())
                    .setMessage("알람음이 설정 되지 않았습니다.\n설정하시겠습니까?")
                    .setNegativeButton("취소"){dialog,which ->
                        binding.switchRing.isChecked = false
                    }
                    .setPositiveButton("확인"){dialog,which ->
                        setAlarmRingtone()
                    }
                    .create()
                    .show()

            }
        }

        binding.npAmpm.setOnValueChangedListener { picker, oldVal, newVal ->
            val amap = if (newVal == 0){ "오전" } else { "오후" }
            alarmTime = AlarmTime(amap,alarmTime.hour_12,alarmTime.minute)
        }

        binding.npHour.setOnValueChangedListener { picker, oldVal, newVal ->
            alarmTime = AlarmTime(alarmTime.ampm,newVal,alarmTime.minute)
        }

        binding.npMinute.setOnValueChangedListener { picker, oldVal, newVal ->
            alarmTime = AlarmTime(alarmTime.ampm,alarmTime.hour_12,newVal)
        }
    }

    private fun registerObserve() {
        alarmSettingViewModel.alarmDetailCreateModifyResult.observe(viewLifecycleOwner){
            if (it.isSuccess){

                Snackbar.make(binding.root,"개인 알람 저장에 성공했습니다.",Snackbar.LENGTH_SHORT).show()
                alarmViewModel.selectedAlarm?.let { alarm ->
                    alarmViewModel.getAlarmList(alarm.teamId)
                }
                mainActivity.navigate(Constants.ALARMSETTING_TO_ALARM)
            } else {
                Snackbar.make(binding.root,"개인 알람 저장에 실패했습니다.",Snackbar.LENGTH_SHORT).show()
            }
        }

        alarmSettingViewModel.savedAlarmDetail.observe(viewLifecycleOwner){ alarmDetail ->
            alarmDetail?.let {
                alarmTime = AlarmTime(alarmDetail.hour,alarmDetail.minute)
                binding.npHour.value = alarmTime.hour_12
                binding.npMinute.value = alarmTime.minute
                binding.npAmpm.value =
                    if (alarmTime.ampm == "오전") {
                        0
                    } else {
                        1
                    }
                if (!it.ringtoneUri.isNullOrBlank()){
                    this.ringtoneUri = Uri.parse(it.ringtoneUri)
                    binding.tvAlarmRingName.text = getRingtoneName(this.ringtoneUri!!)
                }
                binding.switchRing.isChecked = alarmDetail.isRingtoneOn
                binding.switchVibrate.isChecked = alarmDetail.isVibrateOn
                binding.switchRainVoice.isChecked = alarmDetail.forecast
                binding.switchMemoVoice.isChecked = alarmDetail.memoVoice
                binding.tilMemo.editText!!.setText(alarmDetail.memo)
                binding.tvRepeat.text = "${alarmDetail.repeatTime}회"
            } ?: run {
                Snackbar.make(binding.root,"알람 호출 실패했습니다.",Snackbar.LENGTH_SHORT).show()
                mainActivity.navigate(Constants.ALARMSETTING_TO_ALARM)
            }
        }
    }

    private fun setNumberpicker() {
        binding.npAmpm.apply {
            minValue = 0
            maxValue = 1
            displayedValues = arrayOf("오전", "오후")
        }
        binding.npHour.apply {
            minValue = 1
            maxValue = 12
            wrapSelectorWheel = true
            setOnValueChangedListener { picker, oldVal, newVal ->
                // 11시에서 12시로 넘어가거나 그 반대의 경우에 '오전/오후' 상태를 변경
                if ((oldVal == 11 && newVal == 12) || (oldVal == 12 && newVal == 11)) {
                    binding.npAmpm.value = if (binding.npAmpm.value == 0) 1 else 0
                }
            }
        }
        binding.npMinute.apply {
            minValue = 0
            maxValue = 59
            displayedValues = (0..59).map {
                it.toString().let {
                    if (it.length == 1) {
                        "0" + it
                    } else it
                }
            }.toTypedArray()
            wrapSelectorWheel = true
        }
    }

    private fun setAlarmRingtone(){
        val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER).apply {
            putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM)
            putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "알람음 설정")
            putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, null as Uri?)
        }
        ringtoneActivityResultLauncher.launch(intent)
    }

    private val ringtoneActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val ringtoneUri: Uri? = result.data?.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)
            if (ringtoneUri != null) {
                this.ringtoneUri = ringtoneUri
                binding.tvAlarmRingName.text = getRingtoneName(ringtoneUri)
                binding.switchRing.isChecked = true
            }
        }
    }

    private fun getRingtoneName(uri: Uri): String{
        return RingtoneManager.getRingtone(requireContext(),uri).getTitle(requireContext())
    }

}