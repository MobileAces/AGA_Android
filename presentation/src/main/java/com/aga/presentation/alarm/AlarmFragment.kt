package com.aga.presentation.alarm

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.aga.presentation.MainViewModel
import com.aga.presentation.R
import com.aga.presentation.base.BaseFragment
import com.aga.presentation.databinding.FragmentAlarmBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.DayOfWeek

@AndroidEntryPoint
class AlarmFragment : BaseFragment<FragmentAlarmBinding>(
    FragmentAlarmBinding::bind, R.layout.fragment_alarm
) {
    private val mainViewModel: MainViewModel by activityViewModels()
    private val alarmViewModel: AlarmViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        registerListener()
        registerObserve()
    }

    private fun registerListener() {
        binding.ibAddNewAlarm.setOnClickListener {
            AlarmCreateDialog(requireContext())
                .setCancelListener { dialog ->
                    dialog.dismiss()
                }
                .setCreateListener { dialog, selectedDay, alarmname ->
                    if (checkValidationInput(alarmname, selectedDay)) {
                        alarmViewModel.createAlarm(alarmname, selectedDay, mainViewModel.teamId)
                        dialog.dismiss()
                    }
                }
                .show()
        }
    }

    private fun registerObserve() {
        alarmViewModel.alarmCreateResult.observe(viewLifecycleOwner) { result ->
            if (result.isSuccess){
                showToast("알람 생성에 성공했습니다.")
            } else {
                showToast("알람 생성에 실패했습니다 code:${result.exceptionOrNull()?.message}")
            }
        }
    }

    private fun checkValidationInput(alarmName: String, selectedDay: Set<DayOfWeek>): Boolean {
        return if (alarmName.trim().length < 3) {
            showToast("알람 이름은 3자 이상이어야합니다.")
            false
        } else if (selectedDay.isEmpty()) {
            showToast("최소 하나의 요일을 선택해야합니다.")
            false
        } else {
            true
        }
    }

}