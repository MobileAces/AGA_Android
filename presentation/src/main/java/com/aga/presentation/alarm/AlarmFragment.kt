package com.aga.presentation.alarm

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.aga.domain.model.AlarmWithDetailList
import com.aga.presentation.MainViewModel
import com.aga.presentation.R
import com.aga.presentation.base.BaseFragment
import com.aga.presentation.base.Constants
import com.aga.presentation.base.PrefManager
import com.aga.presentation.databinding.FragmentAlarmBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.DayOfWeek

private val TAG = "AlarmFragment_AWESOME"

@AndroidEntryPoint
class AlarmFragment : BaseFragment<FragmentAlarmBinding>(
    FragmentAlarmBinding::bind, R.layout.fragment_alarm
) {
    private val mainViewModel: MainViewModel by activityViewModels()
    private val alarmViewModel: AlarmViewModel by viewModels()

    private var alarmListAdapter: AlarmListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        registerListener()
        registerObserve()
        getAlarmList(mainViewModel.teamId)
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
        alarmViewModel.alarmCreateResult.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {result ->
                if (result.isSuccess){
                    showToast("알람 생성에 성공했습니다.")
                    getAlarmList(mainViewModel.teamId)
                } else {
                    showToast("알람 생성에 실패했습니다 code:${result.exceptionOrNull()?.message}")
                }
            }
        }

        alarmViewModel.alarmListResult.observe(viewLifecycleOwner){ result ->
            if (result.isSuccess){
                result.getOrDefault(listOf()).let {alarmWithDetailLists ->
                    setAlarmListAdapter(alarmWithDetailLists)
                }
            } else {
                showToast("알람목록 호출에 실패했습니다.")
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

    /**
     * 알람 목록 리스트 어뎁터 및 UI 적용
     */
    private fun setAlarmListAdapter(alarmWithDetailLists: List<AlarmWithDetailList>){
        val myId = PrefManager.read(Constants.PREF_USER_ID,null)
        val teamMemberList = mainViewModel.teamMemberList.value

        if (!myId.isNullOrBlank() && !teamMemberList.isNullOrEmpty()){
            if (binding.rvAlarmList.adapter == null){
                if (alarmListAdapter == null){
                    alarmListAdapter = AlarmListAdapter(myId,alarmWithDetailLists,teamMemberList)
                }
                binding.rvAlarmList.adapter = alarmListAdapter
            } else {
                alarmListAdapter?.changeDataSet(alarmWithDetailLists)
            }
        } else {
            showToast("올바르지 않은 접근입니다.")
        }
    }

    /**
     * 알람 목록 호출
     */
    private fun getAlarmList(teamId: Int){
        if (teamId >= 0){
            alarmViewModel.getAlarmList(teamId)
        } else {
            showToast("올바르지 않은 접근입니다.")
        }
    }
}