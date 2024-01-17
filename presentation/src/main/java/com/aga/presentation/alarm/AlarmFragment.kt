package com.aga.presentation.alarm

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.aga.domain.model.Alarm
import com.aga.domain.model.AlarmDetail
import com.aga.domain.model.AlarmWithDetailList
import com.aga.presentation.MainActivity
import com.aga.presentation.MainViewModel
import com.aga.presentation.R
import com.aga.presentation.base.BaseFragment
import com.aga.presentation.base.Constants
import com.aga.presentation.base.PrefManager
import com.aga.presentation.databinding.FragmentAlarmBinding
import com.aga.presentation.util.TwoButtonSnackBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.time.DayOfWeek

private val TAG = "AlarmFragment_AWESOME"

@AndroidEntryPoint
class AlarmFragment : BaseFragment<FragmentAlarmBinding>(
    FragmentAlarmBinding::bind, R.layout.fragment_alarm
) {
    private lateinit var mainActivity: MainActivity
    private val mainViewModel: MainViewModel by activityViewModels()
    private val alarmViewModel: AlarmViewModel by activityViewModels()

    private var alarmListAdapter: AlarmListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = requireActivity() as MainActivity
        registerListener()
        registerObserve()
        if (alarmViewModel.alarmListResult.value == null){
            getAlarmList(mainViewModel.teamId)
            Log.d(TAG, "onViewCreated: ")
        }
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

        alarmViewModel.alarmDetailModifyResult.observe(viewLifecycleOwner){result ->
            if (result.isSuccess && targetSwitch != null && targetAlarmDetail != null){
                result.getOrNull()?.let {alarmDetail ->
                    // 타켓 알람디테일 isOn 변수 수정 후 target 변수 해제
                    targetAlarmDetail!!.isOn = alarmDetail.isOn
                    if (alarmDetail.isOn){
                        targetSwitch!!.on()
                    } else {
                        targetSwitch!!.off()
                    }
                    targetAlarmDetail = null
                    targetSwitch = null
                }
            } else {
                showToast("알람 수정에 실패했습니다.")
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
                    alarmListAdapter = AlarmListAdapter(myId,alarmWithDetailLists,teamMemberList,alarmSwitchClickListener)
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

    /**
     * 알람 스위치 클릭 리스너
     */
    private var targetSwitch: AlarmListAdapter.AlarmListViewHolder.SwitchAccessWrapper? = null
    private var targetAlarmDetail: AlarmDetail? = null
    private var alarmSwitchClickListener: (
        AlarmListAdapter.AlarmListViewHolder.SwitchAccessWrapper, AlarmDetail?, Alarm
    ) -> Unit = {switchWrapper, alarmDetail, alarm ->
        targetSwitch = switchWrapper
        if (alarmDetail == null){
            TwoButtonSnackBar(binding.root,"기존 알람이 없습니다. 생성하시겠습니까?",Snackbar.LENGTH_SHORT)
                .setConfirmClickListener {
                    alarmViewModel.selectedAlarm = alarm
                    mainActivity.navigate(Constants.ALARM_TO_ALARMSETTING)
                }
                .show()
        } else {
            alarmViewModel.modifyAlarmDetail(alarmDetail.copy(isOn = !alarmDetail.isOn))
        }
    }

}