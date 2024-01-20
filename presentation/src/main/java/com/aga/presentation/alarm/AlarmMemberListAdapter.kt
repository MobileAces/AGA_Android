package com.aga.presentation.alarm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aga.domain.model.AlarmDetail
import com.aga.domain.model.TeamMember
import com.aga.presentation.R
import com.aga.presentation.databinding.ItemAlarmListMemberBinding

class AlarmMemberListAdapter(
    private val teamMemberList: List<TeamMember>,
    private val alarmDetailList: List<AlarmDetail>
): RecyclerView.Adapter<AlarmMemberListAdapter.AlarmMemberListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmMemberListViewHolder {
        return AlarmMemberListViewHolder(
            ItemAlarmListMemberBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AlarmMemberListViewHolder, position: Int) {
        holder.bind(teamMemberList[position],alarmDetailList)
    }

    override fun getItemCount(): Int {
        return teamMemberList.size
    }

    class AlarmMemberListViewHolder(
        private val binding: ItemAlarmListMemberBinding,
    ): RecyclerView.ViewHolder(binding.root){

        private var userNickName: String? = null
        private var alarmTime: AlarmTime? = null
        private var alarmTimeSetFlag = false

        fun bind(teamMember: TeamMember, alarmDetailList: List<AlarmDetail>){
            // 기본 변수 세팅
            if (userNickName == null){
                userNickName = teamMember.userNickname
            }
            if (!alarmTimeSetFlag){
                alarmDetailList.find {
                    teamMember.userId == it.userId
                }?.let {
                    if (it.isOn){
                        alarmTime = AlarmTime(it.hour,it.minute)
                    }
                }
                alarmTimeSetFlag = true
            }

            binding.tvName.text = teamMember.userNickname
            alarmTime?.let {
                binding.tvAmPm.text = it.ampm
                binding.tvTime.text = it.get12HourTimeString()
            } ?: run {
                binding.tvAmPm.text = binding.root.context.getString(R.string.alarm_no_setting)
            }
        }
    }
}