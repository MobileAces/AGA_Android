package com.aga.presentation.alarm

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
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
                            "",
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
                            "",
                            alarmViewModel.selectedAlarm!!.teamId
                        ).also { AgaAlarmManager.setNewAlarm(it,requireContext()) }
                    )
                }
            } else {
                showToast("올바르지 않은 접근입니다.")
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

}