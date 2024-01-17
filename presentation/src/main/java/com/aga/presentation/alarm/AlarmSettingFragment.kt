package com.aga.presentation.alarm

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import com.aga.presentation.MainActivity
import com.aga.presentation.R
import com.aga.presentation.base.BaseFragment
import com.aga.presentation.base.Constants
import com.aga.presentation.databinding.FragmentAlarmSettingBinding

class AlarmSettingFragment : BaseFragment<FragmentAlarmSettingBinding>(
    FragmentAlarmSettingBinding::bind, R.layout.fragment_alarm_setting
) {

    private lateinit var mainActivity: MainActivity
    private val alarmViewModel: AlarmViewModel by activityViewModels()
    private lateinit var weekDaySelectManager: WeekDaySelectManager
    private lateinit var alarmTime: AlarmTime

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
        registerListener()
    }

    private fun initUi() {
        setNumberpicker()
        weekDaySelectManager.setAllEnable(false)
        // Alarm 관련
        alarmViewModel.selectedAlarm?.let {alarm ->
            binding.tvAlarmTitle.text = alarm.alarmName
            weekDaySelectManager.setSelectedDay(alarm.alarmDay)
        }

        // AlarmDetail 관련
        alarmViewModel.selectedAlarmDetail?.let { alarmDetail ->
            alarmTime = AlarmTime(alarmDetail.hour,alarmDetail.minute)
            binding.switchRing.isChecked = alarmDetail.isOn
            binding.switchVibrate.isChecked = alarmDetail.isOn
            binding.switchRainVoice.isChecked = alarmDetail.forecast
            binding.switchMemoVoice.isChecked = alarmDetail.memoVoice
            binding.tilMemo.editText!!.setText(alarmDetail.memo)
            binding.tvRepeat.text = "${alarmDetail.repeatTime}회"
        } ?: run {
            alarmTime = AlarmTime(6, 0)
        }
        // 알람 시간
        binding.npHour.value = alarmTime.hour_12
        binding.npMinute.value = alarmTime.minute
        binding.npAmpm.value =
            if (alarmTime.ampm == "오전") {
                0
            } else {
                1
            }
    }

    private fun registerListener() {

    }

    private fun registerObserve() {

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