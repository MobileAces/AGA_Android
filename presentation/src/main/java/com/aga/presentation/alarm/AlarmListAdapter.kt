package com.aga.presentation.alarm

import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.aga.domain.model.Alarm
import com.aga.domain.model.AlarmDetail
import com.aga.domain.model.AlarmWithDetailList
import com.aga.domain.model.TeamMember
import com.aga.presentation.R
import com.aga.presentation.databinding.ItemAlarmListBinding
import com.aga.presentation.util.collapse
import com.aga.presentation.util.expand
import com.google.android.material.materialswitch.MaterialSwitch
import java.time.DayOfWeek

private val TAG = "AlarmListAdapter_AWESOME"

class AlarmListAdapter(
    private val myId: String,
    private var alarmList: List<AlarmWithDetailList>,
    private var teamMemberList: List<TeamMember>,
    private var switchClickListener: (
        switchWrapper: AlarmListViewHolder.SwitchAccessWrapper,
        alarmDetail: AlarmDetail?,
        alarm: Alarm
    ) -> Unit,
    private var alarmSettingClickListener: (View, Alarm, AlarmDetail?) -> Unit
) : RecyclerView.Adapter<AlarmListAdapter.AlarmListViewHolder>() {

    private val dayList = arrayOf(
        DayOfWeek.SUNDAY,
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY,
        DayOfWeek.SATURDAY
    )

    init {
        // 본인 아이디 제외한 멤버 목록만 정리 후 멤버 목록에 적용
        teamMemberList = teamMemberList.filter { it.userId != myId }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmListViewHolder {
        return AlarmListViewHolder(
            ItemAlarmListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AlarmListViewHolder, position: Int) {
        holder.bind(
            alarmList[position],
            teamMemberList,
            dayList,
            myId,
            switchClickListener,
            alarmSettingClickListener
        )
    }

    override fun getItemCount(): Int {
        return alarmList.size
    }

    fun changeDataSet(alarmList: List<AlarmWithDetailList>) {
        this.alarmList = alarmList
        notifyDataSetChanged()
    }

    class AlarmListViewHolder(
        val binding: ItemAlarmListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private var alarmMemberListAdapter: AlarmMemberListAdapter? = null
        private var myAlarmTime: AlarmTime? = null
        private var switchAccessWrapper: SwitchAccessWrapper = SwitchAccessWrapper(binding.switchOnOff)
        private var myAlarmDetail: AlarmDetail? = null

        fun bind(
            item: AlarmWithDetailList,
            teamMemberList: List<TeamMember>,
            dayArray: Array<DayOfWeek>,
            myId: String,
            switchClickListener: (switchWrapper: SwitchAccessWrapper, alarmDetail: AlarmDetail?, alarm: Alarm) -> Unit,
            alarmSettingClickListener: (View, Alarm, AlarmDetail?) -> Unit
        ) {
            binding.tvAlarmTitle.text = item.alarmName
            binding.tvApplyDay.text = getSelectedDaySpannableString(
                item.alarmDay,
                dayArray,
                binding.tvApplyDay.text.toString()
            )
            binding.viewSwitchWrapper.setOnClickListener {
                switchClickListener(
                    switchAccessWrapper,
                    myAlarmDetail,
                    Alarm(item.alarmId,item.alarmName,item.alarmDay.toSet(),item.teamId))
            }
            binding.ibAlarmSetting.setOnClickListener {
                alarmSettingClickListener(
                    it,
                    Alarm(item.alarmId,item.alarmName,item.alarmDay.toSet(),item.teamId),
                    myAlarmDetail
                )
                Log.d(TAG, "bind: setting clicked")
            }
            setMemberListAdapter(teamMemberList, item.alarmDetailList)
            setShowMemberButton()
            // 알림 디테일 리스트 확인 후 로직
            item.alarmDetailList.find { it.userId == myId }?.let {
                setAlarmTime(it.hour, it.minute)
                setMyAlarmDetail(it)
                binding.switchOnOff.isChecked = it.isOn
            } ?: run {
                binding.tvAmPm.text = binding.root.context.getString(R.string.alarm_no_setting)
                binding.switchOnOff.isChecked = false
            }
        }

        // 알람 시간 설정
        private fun setAlarmTime(hour: Int, minute: Int) {
            if (myAlarmTime == null) {
                myAlarmTime = AlarmTime(hour, minute)
            }
            myAlarmTime?.let {
                binding.tvAmPm.text = it.ampm
                binding.tvTime.text = it.get12HourTimeString()
            }
        }

        private fun setMyAlarmDetail(alarmDetail: AlarmDetail) {
            if (myAlarmDetail == null) {
                myAlarmDetail = alarmDetail
            }
        }

        // 알람별 멤버 리스트 어뎁터
        private fun setMemberListAdapter(
            teamMemberList: List<TeamMember>, alarmDetailList: List<AlarmDetail>
        ) {
            if (alarmMemberListAdapter == null) {
                alarmMemberListAdapter = AlarmMemberListAdapter(teamMemberList, alarmDetailList)
            }
            binding.rvMemberList.adapter = alarmMemberListAdapter
        }

        // 멤버 리스트 표출
        private fun setShowMemberButton() {
            binding.ivShowAlarmSettingMembers.setOnClickListener {
                if (binding.rvMemberList.visibility == View.GONE) {
                    binding.rvMemberList.expand()
                    binding.ivShowAlarmSettingMembers.setImageResource(
                        R.drawable.ic_arrow_up
                    )
                } else if (binding.rvMemberList.visibility == View.VISIBLE) {
                    binding.rvMemberList.collapse()
                    binding.ivShowAlarmSettingMembers.setImageResource(
                        R.drawable.ic_arrow_down
                    )
                }
            }
        }

        // 선택된 요일 텍스트 색상 변환
        private fun getSelectedDaySpannableString(
            selectedDaySet: Set<DayOfWeek>, dayArray: Array<DayOfWeek>, viewString: String
        ): SpannableString {
            val spannableString = SpannableString(viewString)
            dayArray.forEachIndexed { i, dayOfWeek ->
                if (selectedDaySet.contains(dayOfWeek)) {
                    spannableString.setSpan(
                        ForegroundColorSpan(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.blue_deep_sea
                            )
                        ),
                        i, i + 1, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            }
            return spannableString
        }

        class SwitchAccessWrapper(private val switch: MaterialSwitch) {
            fun on() {
                switch.isChecked = true
            }

            fun off() {
                switch.isChecked = false
            }

            fun getStateWithChange(): Boolean {
                switch.isChecked = !switch.isChecked
                return switch.isChecked
            }
        }
    }
}